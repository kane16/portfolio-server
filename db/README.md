# DB build and export tooling

This folder contains scripts to export MongoDB data, generate an initialization JS script, and build a ready-to-use
MongoDB Docker image for different environments (`dev/`, `test/`).

## Requirements

- Python 3.9+
- Install dependencies:

```bash
pip install -r requirements.txt
```

## Quick start

Set connection info with env vars or CLI flags.

- From env:
    - `MONGODB_URI` (default: `mongodb://localhost:27017`)
    - `MONGODB_DB`

Examples:

```bash
python import_current_db_state.py --db mydb
python import_current_db_state.py --uri mongodb://localhost:27017/mydb
python import_current_db_state.py --db mydb --out ./data/snapshot
python import_current_db_state.py --db mydb --ndjson
```

- JSON array format writes `[ ... ]` with pretty indentation.
- `--ndjson` writes one JSON object per line, suitable for streaming tools.

The script also writes a small `export_manifest.json` with metadata and per-collection counts.

## Orchestrator: build_env_db.sh

The root script `build_env_db.sh` orchestrates the full flow for an environment folder (e.g., `dev` or `test`). It uses
two Python subscripts and a Docker build step:

1. Export current DB state into JSON files

- Runs `import_current_db_state.py` and writes files to `<ENV>/data/`.

1. Merge JSON data into a Mongo init JS script

- Runs `merge_data_files_into_js_script.py` to produce `<ENV>/init_script.js` with `db.createCollection` and
  `insertMany(...)` calls. It wraps ISO date strings into `new Date("...")` so Mongo stores them as Date types.

1. Build a Mongo image for the environment

- Changes into `<ENV>/` and runs `./create_db_image.sh` which builds the Docker image defined by the Dockerfile in that
  folder.

### Usage

Requires a Python virtual environment at `.venv/` with requirements installed.

Run the whole pipeline for `test`:

```bash
./build_env_db.sh test
```

Run only a specific step:

```bash
# Only export JSON into test/data
./build_env_db.sh test import_db

# Only merge JSON into test/init_script.js
./build_env_db.sh test merge_data

# Only build the Docker image from test/
./build_env_db.sh test create_image
```

Notes:

- The orchestrator activates `.venv` before running Python scripts.
- JSON files are generated under `<ENV>/data/`. The merge step reads from that folder.
