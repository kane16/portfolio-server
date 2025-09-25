package pl.delukesoft.portfolioserver.application.resume.skill

data class SkillDTO(
  val name: String,
  val level: Int,
  val description: String?,
  val domains: List<String>
)
