package pl.delukesoft.portfolioserver.application.skill

data class SkillDTO(
  val id: Long?,
  val name: String,
  val level: Int,
  val description: String,
  val domains: List<String>
)
