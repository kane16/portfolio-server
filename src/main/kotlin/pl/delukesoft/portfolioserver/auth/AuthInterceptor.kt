package pl.delukesoft.portfolioserver.auth

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.auth.exception.AuthorizationException

@Aspect
@Component
class AuthInterceptor(
  private val authRequestService: AuthRequestService,
) {

  private val log = LoggerFactory.getLogger(AuthInterceptor::class.java)

  @Before("@annotation(authRequired) && args(token)", argNames = "authRequired, token")
  fun interceptToken(authRequired: AuthRequired, token: String) {
    log.debug("Intercepting token: $token")
    val authorities = authRequestService.getUserAuthorities(token)
    when (authorities.any { role -> authRequired.role == role }) {
      false -> throw AuthorizationException("User does not have required role: ${authRequired.role}")
      true -> log.info("User successfully authenticated with role: ${authRequired.role}")
    }
  }

}