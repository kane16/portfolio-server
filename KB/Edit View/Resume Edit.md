[[Edit Page]] is shown to the signed in user, error for unauthorized. 
### 1. Resume pulling flow

```plantuml
actor ROLE_CANDIDATE as candidate
actor ROLE_ADMIN as admin
participant System as system
boundary Auth as auth
database portfolio_db as db

group Edit candidate Resume
candidate -> system: /edit
system -> candidate: Lazily loaded PortfolioEdit.tsx
candidate -> system: getPortfolioById(id: Long)
system -> auth: getRolesForUser(token: String)
auth -> system: <font color=green> 200 ROLE_CANDIDATE
system -> db: findByIdAndUser(id: Long, user: User)
db -> system: Resume
system -> candidate: <font color=green> 200 EditPortfolioDTO
end

group Edit different Resume
candidate -> system: /edit
system -> candidate: Lazily loaded PortfolioEdit.tsx
user -> system: getPortfolioById(id: Long)
system -> auth: getRolesForUser(token: String)
auth -> system: <font color=green> 200 ROLE_CANDIDATE
system -> db: findByIdAndUser(id: Long, user: User)
db -> system:  <font color=red> None
system -> candidate: <font color=red> 404 Portfolio not found
end

group Edit candidate portfolio
admin -> system: /edit
system -> admin: Lazily loaded PortfolioEdit.tsx
admin -> system: getPortfolioById(id: Long)
system -> auth: getRolesForUser(token: String)
auth -> system: <font color=green> 200 ROLE_ADMIN
system -> db: findById(id: Long)
db -> system: Resume
system -> admin: <font color=green> 200 EditPortfolioDTO
end

group Edit different portfolio
admin -> system: /edit
system -> admin: Lazily loaded PortfolioEdit.tsx
admin -> system: getPortfolioById(id: Long)
system -> auth: getRolesForUser(token: String)
auth -> system: <font color=green> 200 ROLE_ADMIN
system -> db: findById(id: Long)
db -> system:  <font color=red> None
system -> admin: <font color=red> 404 Portfolio not found
end

```



### 3. Edit model

Edit page users [[Write Resume model]] to read different versions, store and operate on them.

>[!Important]
>View will use [[Application connector model]]