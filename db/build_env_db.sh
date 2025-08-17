#!/bin/bash

ENV=$1
PARTIAL=$2

if [[ "$PARTIAL" ]]; then
  if [[ "$PARTIAL" == "import_db" ]]; then
    source .venv/bin/activate
    python import_current_db_state.py --db portfolio --out $ENV/data
  fi
  if [[ "$PARTIAL" == "create_image" ]]; then
    cd $ENV
    ./create_db_image.sh
  fi
  if [[ "$PARTIAL" == "merge_data" ]]; then
    source .venv/bin/activate
    python merge_data_files_into_js_script.py --env $ENV
  fi
else
  source .venv/bin/activate
  python import_current_db_state.py --db portfolio --out $ENV/data
  python merge_data_files_into_js_script.py --env $ENV
  cd $ENV
  ./create_db_image.sh
fi