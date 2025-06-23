package pl.delukesoft.portfolioserver.application.portfolio.model

data class SkillDTO(
  val name: String,
  val description: String? = null,
  val level: Int? = null,
)
