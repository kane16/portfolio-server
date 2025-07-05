### 1. Resume Flow for Candidate

```plantuml
actor ROLE_CANDIDATE as user
participant System as system
boundary Auth as auth
database portfolio_db as db

group Edit candidate Resume
user -> system: /edit
system -> user: Lazily loaded PortfolioEdit.tsx
user -> system: getPortfolioById(id: Long)
system -> auth: getRolesForUser(token: String)
auth -> system: <font color=green> 200 ROLE_CANDIDATE
system -> db: findByIdAndUser(id: Long, user: User)
db -> system: Resume
system -> user: <font color=green> 200 EditPortfolioDTO
end

group Edit different Resume
user -> system: /edit
system -> user: Lazily loaded PortfolioEdit.tsx
user -> system: getPortfolioById(id: Long)
system -> auth: getRolesForUser(token: String)
auth -> system: <font color=green> 200 ROLE_CANDIDATE
system -> db: findByIdAndUser(id: Long, user: User)
db -> system:  <font color=red> None
system -> user: <font color=red> 404 Portfolio not found
end

group Open Candidate Resume
user -> system: /edit/opened
system -> auth: getRolesForUser(token: String)
end

```

### 2. Resume Flow for Admin

```plantuml
actor ROLE_ADMIN as user
participant System as system
boundary Auth as auth
database portfolio_db as db

group Edit candidate portfolio
user -> system: /edit
system -> user: Lazily loaded PortfolioEdit.tsx
user -> system: getPortfolioById(id: Long)
system -> auth: getRolesForUser(token: String)
auth -> system: <font color=green> 200 ROLE_ADMIN
system -> db: findById(id: Long)
db -> system: Resume
system -> user: <font color=green> 200 EditPortfolioDTO
end

group Edit different portfolio
user -> system: /edit
system -> user: Lazily loaded PortfolioEdit.tsx
user -> system: getPortfolioById(id: Long)
system -> auth: getRolesForUser(token: String)
auth -> system: <font color=green> 200 ROLE_ADMIN
system -> db: findById(id: Long)
db -> system:  <font color=red> None
system -> user: <font color=red> 404 Portfolio not found
end

```
