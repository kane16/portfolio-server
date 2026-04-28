package pl.delukesoft.portfolioserver.security

data class User(
  val username: String,
  val email: String,
  val roles: List<String> = emptyList(),
  val firstname: String,
  val lastname: String
)
