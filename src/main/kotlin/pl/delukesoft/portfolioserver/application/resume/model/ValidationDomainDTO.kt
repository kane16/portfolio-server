package pl.delukesoft.portfolioserver.application.resume.model

enum class ValidationDomainDTO(val label: String, val weight: Double) {
  SHORTCUT("shortcut", 0.1),
  SKILLS("skill", 0.15),
  EXPERIENCES("experience", 0.3),
  SIDE_PROJECTS("sideProject", 0.2),
  HOBBIES("hobby", 0.1),
  LANGUAGES("language", 0.15)
}