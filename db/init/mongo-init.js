db = db.getSiblingDB(process.env.MONGO_INITDB_DATABASE);

db.createUser({
  user: process.env.PORTFOLIO_MONGO_APP_USERNAME,
  pwd: process.env.PORTFOLIO_MONGO_APP_PASSWORD,
  roles: [{ role: 'dbOwner', db: process.env.MONGO_INITDB_DATABASE }],
});
