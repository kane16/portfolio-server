package pl.delukesoft.portfolioserver.adapters.auth

data class User(
  val id: Long,
  val username: String,
  val email: String,
  val roles: List<String> = emptyList()
)
