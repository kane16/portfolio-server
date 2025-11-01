# Endpoints for Resume manipulation (editing lifecycle)

Base path: `/resume` (`ResumeController`)

- GET `/resume/{id}` — Get resume for editing
    - Auth: `ROLE_CANDIDATE`
    - Response: `ResumeEditDTO`

- GET `/resume/history` — Get user resume versions history
    - Auth: `ROLE_CANDIDATE`
    - Response: `ResumeHistoryDTO` (contains optional default/published resume and a list of versions)

- POST `/resume/edit/init` — Initiate a new resume (create from shortcut)
    - Auth: `ROLE_CANDIDATE`
    - Request: `ResumeShortcutDTO`
    - Response: `Boolean` (true on success)
    - Status: 201 Created

- PUT `/resume/edit/{id}` — Update resume shortcut (title/summary/image)
    - Auth: `ROLE_CANDIDATE`
    - Request: `ResumeShortcutDTO`
    - Response: `Boolean`

- PUT `/resume/edit/{version}/publish` — Publish a specific resume version
    - Auth: `ROLE_CANDIDATE`
    - Response: `Boolean`
    - Errors: `ResumeOperationNotAllowed` if a version is already published; `ResumeNotFound` if version id invalid

- PUT `/resume/edit/unpublish` — Unpublish currently published version
    - Auth: `ROLE_CANDIDATE`
    - Response: `Boolean`
    - Errors: `ResumeOperationNotAllowed` if nothing is published
