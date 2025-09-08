
let db = db.getSiblingDB("portfolio");

db.createUser({user: "root", pwd: "pass", roles: [{role: "readWrite", db: "portfolio"}]});
