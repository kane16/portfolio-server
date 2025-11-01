# Concepts: Domain vs DTOs

- Domain model (backend only):
    - `pl.delukesoft.portfolioserver.domain.resume.Resume`
    - `pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory`, `ResumeVersion` (stateful versioning &
      publishing)
    - Various value objects under `domain.resume.*` (skills, experience, education, timeframe, etc.)

- DTOs (transfer objects exposed by the application layer):
    - Edit DTO (editing a resume):
        - `pl.delukesoft.portfolioserver.application.resume.model.ResumeEditDTO`
    - Portfolio view DTOs (presentation-friendly view of a resume):
        - `pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO`
        - `pl.delukesoft.portfolioserver.application.portfolio.model.ResumeDTO` (portfolio/internal mapping type)
        - `pl.delukesoft.portfolioserver.application.portfolio.model.ProjectDTO`, `SkillPortfolioDTO`, `TimespanDTO` (
          used inside portfolio view)
    - Shortcuts & history:
        - `pl.delukesoft.portfolioserver.application.portfolio.model.ResumeShortcutDTO`
        - `pl.delukesoft.portfolioserver.application.portfolio.model.ResumeHistoryDTO`, `ResumeVersionDTO`
    - Building blocks used in both editing and viewing:
        - `pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO`
        - `pl.delukesoft.portfolioserver.application.portfolio.model.LanguageDTO`
        - `pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO` (editing)
        - `pl.delukesoft.portfolioserver.application.resume.education.EducationDTO`
        - `pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO`

Mapping is centralized in `pl.delukesoft.portfolioserver.application.resume.ResumeMapper` and related mappers (e.g.,
`SkillMapper`). The mapper converts the domain `Resume` into `ResumeEditDTO` for editing and into portfolio-oriented
DTOs for display.
