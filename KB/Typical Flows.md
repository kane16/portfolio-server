# Typical flows

1) Initiate a resume

- Call `POST /resume/edit/init` with `ResumeShortcutDTO`.
- Backend creates a new `Resume` for current user. Returns `true`.

2) Update basic shortcut fields

- Call `PUT /resume/edit/{id}` to update title/summary/image.

3) Manage skills on a resume

- Add: `POST /resume/edit/{resumeId}/skills` with `SkillDTO`.
- Edit: `PUT /resume/edit/{resumeId}/skills/{skillName}`.
- Delete: `DELETE /resume/edit/{resumeId}/skills/{skillName}`.
- List: `GET /skills/resume/{resumeId}`.

4) Publish/unpublish

- Publish a version: `PUT /resume/edit/{version}/publish`.
- Unpublish: `PUT /resume/edit/unpublish`.
- Inspect history: `GET /resume/history`.

5) Access as portfolio (for display)

- Default resume: `POST /cv` (optionally with `PortfolioSearch`).
- Concrete resume: `POST /cv/{id}`.
