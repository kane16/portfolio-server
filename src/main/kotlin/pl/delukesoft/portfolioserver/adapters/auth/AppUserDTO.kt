package pl.delukesoft.portfolioserver.adapters.auth

data class AppUserDTO(
  val id: Long,
  val username: String,
  val email: String,
  val firstname: String,
  val lastname: String,
  val roles: List<String>
)