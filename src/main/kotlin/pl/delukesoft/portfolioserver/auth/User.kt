package pl.delukesoft.portfolioserver.auth

data class User(
  val id: Long,
  val username: String,
  val email: String,
  val roles: List<String> = emptyList(),
  val password: String? = null
)
