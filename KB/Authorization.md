# Authorization

Endpoints use `@AuthRequired` with roles:

- `ROLE_CANDIDATE` — resume owner/candidate actions (fetch own resume, edit, publish/unpublish)
- `ROLE_ADMIN` — may access portfolio view by id

JWT/Authorization header is required where annotated.