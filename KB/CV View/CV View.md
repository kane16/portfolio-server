[[CV print]] is page that shows information about the user in structured - CV like way. It allows user to print it, as
it's styled to format print and raw page in correct way.

### 1. CV Access

For specific users there are restrictions on viewing CV:

- Administrator will be able to visit all CV, not only his own.
- Anonymous user will see default site CV (There is only one).
- Candidate will be able to view his own default CV on this page.

> [!NOTE]
> In case of Candidate, by default his CV should be shown first as he enters with authorization, if he doesn't have any
> then application default should be returned.

### 2. Pulling CV flow

```plantuml
actor user
participant System as system

group Default resume
user -> system: GET /pdf
system -> user: 200 Default Resume
end

group Resume by ID
user -> system: GET /pdf/{id}
system -> user: 200 Resume with id
end

```

### 2. Page usage

View will be used as Iframe in [[Portfolio View]] and [[Resume Edit]]

> [!Important]
> View will use [[Application connector model]]

