package pl.delukesoft.portfolioserver.adapters.auth

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthRequired(
  val role: String = "ROLE_USER"
)
