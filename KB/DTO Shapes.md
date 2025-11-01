# DTO shapes (selected)

- `ResumeEditDTO` (editing):
  ```json
  {
    "id": 123,
    "fullname": "string",
    "imageSource": "string",
    "title": "string",
    "summary": "string",
    "skills": [ { "name": "string", "level": 0, "detail": "string", "description": "string", "domains": ["string"] } ],
    "languages": [ { "name": "string", "level": "A1|A2|B1|B2|C1|C2|NATIVE", "id": 1 } ],
    "sideProjects": [ { "id": 1, "business": "string", "position": "string", "summary": "string", "description": "string|null", "timespan": {"start": "YYYY-MM-DD", "end": "YYYY-MM-DD|null"}, "skills": [ { "name": "string", "level": 0, "detail": "string|null", "description": "string", "domains": ["string"] } ] } ],
    "workHistory": [],
    "hobbies": [ "string" ],
    "education": [ { "id": 1, "title": "string", "institution": {"name": "string","city": "string","country": "string"}, "timeframe": {"start": "YYYY-MM-DD","end": "YYYY-MM-DD|null"}, "fieldOfStudy": "string", "grade": "string|null", "type": "DEGREE|COURSE|...", "description": "string|null", "externalLinks": ["url"] } ]
  }
  ```
  Notes:
    - In current mapper implementation, `fullname` is hard-coded to "Łukasz Gumiński". Adapt mapper if multi-user names
      are needed.
    - `imageSource` comes from `resume.shortcut.image.src`.

- `ResumeShortcutDTO` (init/update shortcut):
  ```json
  {
    "title": "string 5..30",
    "summary": "string 30..100",
    "image": { "src": "string", "...": "provider-specific" }
  }
  ```
  Validation: `title` length 5..30, `summary` length 30..100 (see annotations).

- `ResumeHistoryDTO`:
  ```json
  {
    "defaultResume": { "id": 123, "title": "string", "summary": "string", "version": 2, "state": "PUBLISHED|DRAFT|..." } ,
    "versions": [ { "id": 123, "title": "string", "summary": "string", "version": 1, "state": "..." } ]
  }
  ```

- `PortfolioDTO` (view):
  ```json
  {
    "id": 123,
    "fullname": "string",
    "imageSource": "string",
    "title": "string",
    "summary": "string",
    "skills": [ { "name": "string", "detail": "string" } ],
    "languages": [ { "name": "string", "level": "...", "id": 1 } ],
    "sideProjects": [ { "position": "string", "business": "string", "summary": "string", "description": "string", "timespan": {"start": "YYYY.MM", "end": "YYYY.MM"}, "skills": [ { "name": "string", "detail": "string" } ] } ],
    "workHistory": [],
    "hobbies": [ "string" ],
    "education": []
  }
  ```
  Notes:
    - Portfolio view flattens and formats fields for display (e.g., `TimespanDTO` uses `YYYY.MM`).
