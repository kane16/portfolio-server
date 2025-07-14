package pl.delukesoft.portfolioserver.utility.loader.model

data class UploadUser(
  val username: String,
  val email: String,
  val roles: List<String> = emptyList()
)
