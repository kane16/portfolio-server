package pl.delukesoft.portfolioserver.application.portfolio.model

data class PortfolioVersionDTO(
  val id: Long,
  val title: String,
  val summary: String,
  val version: Long,
  val state: String,
) {
}