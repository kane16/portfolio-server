package pl.delukesoft.portfolioserver.application.resume.experience.skill

data class SkillExperienceDTO(
  val skill: String,
  val level: Int,
  val detail: String,
  val domains: List<String>
)
