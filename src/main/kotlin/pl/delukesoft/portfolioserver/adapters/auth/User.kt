package pl.delukesoft.portfolioserver.adapters.auth

data class User(
  val username: String,
  val email: String,
  val roles: List<String> = emptyList()
)
