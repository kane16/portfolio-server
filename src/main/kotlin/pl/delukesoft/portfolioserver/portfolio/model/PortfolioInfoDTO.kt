package pl.delukesoft.portfolioserver.portfolio.model

data class PortfolioInfoDTO(
  val name: String,
  val values: List<PortfolioInfoEntryDTO>
)