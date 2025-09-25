package pl.delukesoft.portfolioserver.application.resume.validation

enum class ValidationDomain(val label: String, val weight: Int, val title: String, val endpoint: String) {
  SHORTCUT("shortcut", 10, "Shortcut", ""),
  SKILLS("skill", 15, "Skills", "skills"),
  EXPERIENCES("experience", 30, "Experience", "experience"),
  SIDE_PROJECTS("sideProject", 20, "Side projects", "side-projects"),
  HOBBIES("hobby", 10, "Hobbies", "hobbies"),
  LANGUAGES("language", 15, "Languages", "languages")
}