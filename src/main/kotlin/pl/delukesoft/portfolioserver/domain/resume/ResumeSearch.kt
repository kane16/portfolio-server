package pl.delukesoft.portfolioserver.domain.resume


data class ResumeSearch(
  val skillNames: List<String>,
  val domainNames: List<String>,
  val businessNames: List<String>
)
