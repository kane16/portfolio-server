package pl.delukesoft.portfolioserver.application.portfolio.model

data class PortfolioHistoryDTO(
  val defaultPortfolio: PortfolioVersionDTO,
  val history: List<PortfolioVersionDTO>
)
