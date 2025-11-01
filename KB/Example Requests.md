# Example requests

- Initiate resume

```http
POST /resume/edit/init
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Senior Developer",
  "summary": "15+ years in JVM, leading teams, delivering cloud systems...",
  "image": { "src": "/static/img/me.jpg" }
}
```

- Publish version

```http
PUT /resume/edit/42/publish
Authorization: Bearer <token>
```

- Get portfolio view by id with search

```http
POST /cv/123
Authorization: Bearer <token>
Content-Type: application/json

{
  "skills": ["Kotlin", "Spring"],
  "domains": ["Backend"],
  "sinceYear": 2019
}
```
