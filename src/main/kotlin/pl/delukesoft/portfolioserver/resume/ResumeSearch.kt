package pl.delukesoft.portfolioserver.resume


data class ResumeSearch(
  val skillNames: List<String>,
  val domainNames: List<String>,
  val businessNames: List<String>
)
