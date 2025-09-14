package pl.delukesoft.portfolioserver.application.portfolio.model

data class SkillPortfolioDTO(
  val name: String,
  val description: String? = null,
  val level: Int? = null,
)
