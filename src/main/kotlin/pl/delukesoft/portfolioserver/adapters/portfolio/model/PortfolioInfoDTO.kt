package pl.delukesoft.portfolioserver.adapters.portfolio.model

data class PortfolioInfoDTO(
  val name: String,
  val values: List<PortfolioInfoEntryDTO>
)