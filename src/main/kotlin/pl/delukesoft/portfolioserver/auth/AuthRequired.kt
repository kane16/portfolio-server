package pl.delukesoft.portfolioserver.auth

@Target(AnnotationTarget.FUNCTION)
annotation class AuthRequired(
  val role: String = "ROLE_USER"
)
