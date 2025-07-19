package pl.delukesoft.portfolioserver.application.portfolio

data class PortfolioSearch(
  val business: List<String> = listOf(),
  val skills: List<String> = listOf(),
  val technologyDomain: List<String> = listOf(),
)