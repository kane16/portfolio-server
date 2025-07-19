package pl.delukesoft.portfolioserver.application.portfolio.model

data class PortfolioDTO(
  val id: Long? = null,
  val title: String,
  val description: String,
  val skills: PortfolioInfoDTO,
  val experience: PortfolioInfoDTO,
  val business: PortfolioInfoDTO,
  val languages: PortfolioInfoDTO,
)
