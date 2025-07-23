let db = db.getSiblingDB('blog');

db.createUser(
  {user: "root", pwd: "pass", roles: [{role: "readWrite", db: "portfolio"}]});