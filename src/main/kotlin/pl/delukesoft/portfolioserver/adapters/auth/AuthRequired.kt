package pl.delukesoft.portfolioserver.adapters.auth

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthRequired(
  vararg val roles: String = ["ROLE_USER"],
  val anonymousAllowed: Boolean = false
)
