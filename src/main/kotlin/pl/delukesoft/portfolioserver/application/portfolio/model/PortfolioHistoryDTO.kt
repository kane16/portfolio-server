package pl.delukesoft.portfolioserver.application.portfolio.model

data class PortfolioHistoryDTO(
  val defaultPortfolio: PortfolioVersionDTO? = null,
  val history: List<PortfolioVersionDTO>
)
