# Skill operations (attached to resume)

Base path mixed under `/skills` and `/resume/edit/{resumeId}/skills` (`SkillController`)

- GET `/skills` — List user skills
    - Auth: `ROLE_CANDIDATE`
    - Response: `List<SkillDTO>`

- GET `/skills/domains` — List skill domains
    - Auth: `ROLE_CANDIDATE`
    - Response: `List<String>`

- POST `/skills/domains` — Add a skill domain
    - Auth: `ROLE_CANDIDATE`
    - Request: body = `String` (domain name)
    - Response: `Boolean`
    - Status: 201 Created

- GET `/skills/resume/{resumeId}` — List skills on a resume
    - Auth: `ROLE_CANDIDATE`
    - Response: `List<SkillDTO>`

- POST `/resume/edit/{resumeId}/skills` — Add skill to resume
    - Auth: `ROLE_CANDIDATE`
    - Request: `SkillDTO`
    - Response: `Boolean`
    - Status: 201 Created
    - Errors: `SkillNotFound` if mapping/cross-ref fails during update operations

- DELETE `/resume/edit/{resumeId}/skills/{skillName}` — Remove skill from resume
    - Auth: `ROLE_CANDIDATE`
    - Response: `Boolean`

- PUT `/resume/edit/{resumeId}/skills/{skillName}` — Edit a specific skill on resume
    - Auth: `ROLE_CANDIDATE`
    - Request: `SkillDTO`
    - Response: `Boolean`