import os
import re
import argparse

def add_collection_with_data_to_script(env, collection_name):
  collection_script = ""
  with open(f'{env}/data/{collection_name}.json', 'r') as file:
    collection_script += "db.createCollection('{}');\n".format(collection_name)
    collection_script += "db.{}.insertMany({});\n".format(collection_name, wrap_dates_with_conversion(file.read()))
  return collection_script

def wrap_dates_with_conversion(collection_script):
  date_regex = r'("\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}.*")'
  collection_script = re.sub(date_regex, lambda x: f"new Date({x.group(1)})", collection_script)
  return collection_script


parser = argparse.ArgumentParser(
    description="Run script with environment-specific settings.")
parser.add_argument(
    "--env",
    choices=["test", "dev"],
    help="Specify the environment: 'test' or 'dev'."
)
args = parser.parse_args()

print(args)

json_files = os.listdir(f'{args.env}/data')
collection_names = [os.path.splitext(f)[0] for f in json_files if f.endswith(
    '.json') and f != 'export_manifest.json']


full_script_content = """
let db = db.getSiblingDB("portfolio");

db.createUser({user: "root", pwd: "pass", roles: [{role: "readWrite", db: "portfolio"}]});
"""

for collection_name in collection_names:
  full_script_content += add_collection_with_data_to_script(args.env, collection_name)

with open(f'{args.env}/init_script.js', 'w') as output_file:
  output_file.write(full_script_content)