package pl.delukesoft.portfolioserver.portfolio.generator.model

data class SkillDTO(
  val name: String,
  val description: String? = null,
  val level: Int? = null,
)
