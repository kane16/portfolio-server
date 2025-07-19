package pl.delukesoft.portfolioserver.application.filter

data class PortfolioSearch(
  val business: List<String> = listOf(),
  val skills: List<String> = listOf(),
  val technologyDomain: List<String> = listOf(),
)