package pl.delukesoft.portfolioserver.application.portfolio.model

data class PortfolioInfoDTO(
  val name: String,
  val values: List<PortfolioInfoEntryDTO>
)