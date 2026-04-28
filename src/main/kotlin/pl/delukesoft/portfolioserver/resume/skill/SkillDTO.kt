package pl.delukesoft.portfolioserver.resume.skill

data class SkillDTO(
  val name: String,
  val level: Int,
  val detail: String?,
  val description: String?,
  val domains: List<String>
)
