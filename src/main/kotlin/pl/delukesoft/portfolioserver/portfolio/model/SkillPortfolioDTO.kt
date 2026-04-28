package pl.delukesoft.portfolioserver.portfolio.model

data class SkillPortfolioDTO(
  val name: String,
  val description: String? = null,
  val level: Int? = null,
)
