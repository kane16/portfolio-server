package pl.delukesoft.portfolioserver.adapters.auth

@Target(AnnotationTarget.FUNCTION)
annotation class AuthRequired(
  val role: String = "ROLE_USER"
)
