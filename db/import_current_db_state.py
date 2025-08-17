#!/usr/bin/env python3
"""
Export all MongoDB collections into separate JSON files in a new data folder.

Defaults:
- URI from env MONGODB_URI (mongodb://localhost:27017 if unset)
- DB name from env MONGODB_DB or from URI path (if provided)
- Output folder: ./data/<YYYYMMDD_HHMMSS>

Usage examples:
  python import_current_db_state.py --db mydb
  python import_current_db_state.py --uri mongodb://localhost:27017/mydb
  python import_current_db_state.py --db mydb --out ./data/snapshot
  python import_current_db_state.py --db mydb --ndjson
"""

from __future__ import annotations

import argparse
import json
import datetime as _dt
import os
import sys
from pathlib import Path
from typing import Iterable, Optional, Any, Dict, List

from pymongo import MongoClient
from pymongo.errors import ConfigurationError
from bson import json_util
from bson.json_util import JSONOptions, DatetimeRepresentation


def _safe_filename(name: str) -> str:
    """Sanitize a collection name to be a safe filename."""
    # Mongo collection names shouldn't have path separators, but sanitize anyway
    invalid = ['\\', '/', '\0']
    for ch in invalid:
        name = name.replace(ch, '_')
    # Spaces to underscores for nicer files
    return name.replace(' ', '_')


def _resolve_output_dir(out: Optional[str]) -> Path:
    ts = _dt.datetime.now().strftime('%Y%m%d_%H%M%S')
    if out:
        base = Path(out)
    else:
        # Default: a "data" folder next to this script, inside repo folder
        base = Path(__file__).resolve().parent / 'data' / ts
    base.mkdir(parents=True, exist_ok=True)
    return base


def _normalize_datetimes(value: Any) -> Any:
    """Recursively convert datetime values to ISO-8601 strings with timezone.

    - Naive datetimes are treated as UTC.
    - Output uses millisecond precision and "+00:00" offset.
    """
    if isinstance(value, _dt.datetime):
        dt = value
        if dt.tzinfo is None:
            dt = dt.replace(tzinfo=_dt.timezone.utc)
        else:
            dt = dt.astimezone(_dt.timezone.utc)
        return dt.isoformat(timespec='milliseconds')
    if isinstance(value, list):
        return [_normalize_datetimes(v) for v in value]
    if isinstance(value, tuple):
        return tuple(_normalize_datetimes(v) for v in value)
    if isinstance(value, dict):
        return {k: _normalize_datetimes(v) for k, v in value.items()}
    return value


def _connect(uri: str) -> MongoClient:
    client: MongoClient = MongoClient(uri, serverSelectionTimeoutMS=7000)
    # Trigger server selection to fail fast if unreachable
    client.admin.command('ping')
    return client


def _get_db(client: MongoClient, db_name: Optional[str]) -> str:
    """Return the database name to use, preferring explicit name over URI default."""
    if db_name:
        return db_name
    # Try get default DB from URI, if provided
    try:
        db = client.get_database()
        if db.name:
            return db.name
    except ConfigurationError:
        pass
    raise SystemExit(
        'Database name not specified. Provide --db or include it in URI.')


def export_collection_as_array(coll, out_file: Path, batch_size: int = 1000, pretty: bool = True) -> int:
    """Export a collection to a single JSON array file. Returns doc count."""
    count = 0
    indent = 2 if pretty else None
    json_opts = JSONOptions(
        datetime_representation=DatetimeRepresentation.ISO8601)
    with out_file.open('w', encoding='utf-8') as f:
        f.write('[\n')
        first = True
        cursor = coll.find({}, no_cursor_timeout=True).batch_size(batch_size)
        try:
            for doc in cursor:
                if not first:
                    f.write(',\n')
                f.write(json_util.dumps(
                    _normalize_datetimes(doc), indent=indent, json_options=json_opts))
                first = False
                count += 1
        finally:
            cursor.close()
        f.write('\n]\n')
    return count


def export_collection_as_ndjson(coll, out_file: Path, batch_size: int = 1000) -> int:
    """Export a collection to NDJSON (one JSON object per line). Returns doc count."""
    count = 0
    json_opts = JSONOptions(
        datetime_representation=DatetimeRepresentation.ISO8601)
    with out_file.open('w', encoding='utf-8') as f:
        cursor = coll.find({}, no_cursor_timeout=True).batch_size(batch_size)
        try:
            for doc in cursor:
                f.write(json_util.dumps(
                    _normalize_datetimes(doc), json_options=json_opts))
                f.write('\n')
                count += 1
        finally:
            cursor.close()
    return count


def main(argv: Optional[Iterable[str]] = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--uri', default=os.getenv('MONGODB_URI', 'mongodb://localhost:27017'),
                        help='MongoDB connection URI (default from MONGODB_URI or mongodb://localhost:27017)')
    parser.add_argument('--db', default=os.getenv('MONGODB_DB'),
                        help='Database name (default from MONGODB_DB or derived from URI if set)')
    parser.add_argument('--out', default=None,
                        help='Output directory (default: ./data/<timestamp> next to this script)')
    parser.add_argument('--collections', nargs='*', default=None,
                        help='Specific collections to export (default: all collections)')
    parser.add_argument('--ndjson', action='store_true',
                        help='Export as newline-delimited JSON per file')
    parser.add_argument('--no-pretty', action='store_true',
                        help='Disable pretty-print for array output')
    parser.add_argument('--batch-size', type=int, default=1000,
                        help='Cursor batch size (default: 1000)')

    args = parser.parse_args(list(argv) if argv is not None else None)

    out_dir = _resolve_output_dir(args.out)
    print(f"Export destination: {out_dir}")

    try:
        client = _connect(args.uri)
    except Exception as e:
        print(f"Failed to connect to MongoDB: {e}", file=sys.stderr)
        return 2

    try:
        db_name = _get_db(client, args.db)
        db = client[db_name]
    except SystemExit as e:
        print(str(e), file=sys.stderr)
        return 2

    try:
        coll_names = args.collections or db.list_collection_names()
    except Exception as e:
        print(f"Failed to list collections: {e}", file=sys.stderr)
        return 2

    if not coll_names:
        print('No collections found to export.')
        return 0

    collections_list: List[Dict[str, Any]] = []
    summary: Dict[str, Any] = {
        'database': db_name,
        'timestamp': _dt.datetime.now().isoformat(),
        'output': str(out_dir),
        'format': 'ndjson' if args.ndjson else 'json-array',
        'collections': collections_list,
    }

    total_docs = 0
    for name in sorted(coll_names):
        safe = _safe_filename(name)
        ext = 'ndjson' if args.ndjson else 'json'
        out_file = out_dir / f"{safe}.{ext}"
        coll = db.get_collection(name)
        print(f"Exporting {name} -> {out_file} ...", flush=True)
        try:
            if args.ndjson:
                count = export_collection_as_ndjson(
                    coll, out_file, args.batch_size)
            else:
                count = export_collection_as_array(
                    coll, out_file, args.batch_size, pretty=not args.no_pretty)
        except Exception as e:
            print(f"  ERROR exporting {name}: {e}", file=sys.stderr)
            continue

        collections_list.append(
            {'name': name, 'file': out_file.name, 'count': count})
        total_docs += count
        print(f"  Done ({count} docs)")

    # Write a small manifest for convenience
    manifest = out_dir / 'export_manifest.json'
    try:
        # Summary uses only built-in types; standard JSON is fine and avoids wrappers
        manifest.write_text(json.dumps(summary, indent=2), encoding='utf-8')
        print(f"Wrote manifest: {manifest}")
    except Exception as e:
        print(f"Failed to write manifest: {e}", file=sys.stderr)

    print(
        f"Export complete: {len(summary['collections'])} collections, {total_docs} documents total.")
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
