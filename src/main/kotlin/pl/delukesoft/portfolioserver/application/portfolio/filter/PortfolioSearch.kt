package pl.delukesoft.portfolioserver.application.portfolio.filter

data class PortfolioSearch(
  val business: List<String> = listOf(),
  val skills: List<String> = listOf(),
  val technologyDomain: List<String> = listOf(),
) {
  fun toCacheKey(): String {
    val sortedBusiness = business.sorted().joinToString(",")
    val sortedSkills = skills.sorted().joinToString(",")
    val sortedTechnologyDomain = technologyDomain.sorted().joinToString(",")
    return "business:[$sortedBusiness];skills:[$sortedSkills];technologyDomain:[$sortedTechnologyDomain]"
  }
}