# Mapping details & notes

`ResumeMapper` highlights:

- `mapResumeToEditDTO(Resume) -> ResumeEditDTO`
    - Copies skills, languages, experiences, hobbies, education with minimal transformation
    - `fullname` currently constant; consider mapping from user profile instead
    - `imageSource` extracted from `shortcut.image.src`
- `mapHistoryToDTO(ResumeHistory) -> ResumeHistoryDTO`
- `mapVersionToDTO(ResumeVersion) -> ResumeVersionDTO`
- `mapShortcutDTOToResume(ResumeShortcutDTO, User) -> ResumeShortcut` (used to initiate/update)
- `mapDTOToExperience(ExperienceDTO, List<Skill>) -> Experience`
- `mapEducationToDTO` and `mapDTOToEducation`

Errors/exceptions surfaced by facade/services:

- `ResumeOperationNotAllowed` — e.g., publishing when already published; unpublishing when none is published
- `ResumeNotFound` — publishing a non-existent version
- `SkillNotFound` — editing/removing a skill not present or mapping target skill missing
