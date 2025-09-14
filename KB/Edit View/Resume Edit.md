[[Edit Main Page]] is shown to the signed in user, error for unauthorized.

### 1. Resume pulling flow

Resume history flow - first step in user dashboard for resumes.

```plantuml

actor user
participant PortfolioServer as system
database PortfolioDB as db
boundary auth_server as auth

group Resume history for ROLE_CANDIDATE user
user -> system: GET /api/portfolio/history
system -> auth: getContextUser
auth -> system: User
system -> db: findHistoryByUsername
db -> system: ResumeHistory
system -> user: PortfolioHistoryDTO
end

```

The “Resume history” functionality allows a candidate to view their previously created or updated resumes when they
first enter the resume section of the user dashboard. When the user, authenticated with the role `ROLE_CANDIDATE`, sends
a `GET /api/portfolio/history` request, the PortfolioServer retrieves the current user’s context from the authentication
server to determine their identity and permissions. Once the authenticated user is confirmed, the system queries the
PortfolioDB to find all resume history records associated with that user’s username. The retrieved history data is then
mapped into a `PortfolioHistoryDTO` and returned to the client, enabling the frontend to present a chronological
overview of the candidate’s resume activity. This process ensures that only the authenticated candidate sees their own
resume history while providing the necessary information for subsequent resume management actions.

```plantuml

actor admin
participant PortfolioServer as system
database PortfolioDB as db
boundary auth_server as auth

group Resume history for ROLE_ADMIN user
admin -> system: GET /api/portfolio/history
system -> auth: getContextUser
auth -> system: User with ROLE_ADMIN priviliges
system -> db: findAllUsersHistory
db -> system: ResumeHistory
system -> admin: PortfolioHistoryDTO
end

```

### 3. Edit model

Edit page users [[Resume model]] to read different versions, store and operate on them.

> [!Important]
> View will use [[Application connector model]]

