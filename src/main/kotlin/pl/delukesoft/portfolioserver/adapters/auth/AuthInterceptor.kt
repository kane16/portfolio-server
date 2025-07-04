package pl.delukesoft.portfolioserver.adapters.auth

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestHeader
import pl.delukesoft.portfolioserver.adapters.auth.exception.AuthorizationException

@Aspect
@Component
class AuthInterceptor(
  private val authRequestService: AuthRequestService,
) {

  private val log = LoggerFactory.getLogger(AuthInterceptor::class.java)

  @Before("@annotation(authRequired) && args(.., token)", argNames = "authRequired, token")
  fun interceptToken(authRequired: AuthRequired, token: String? = null) {
    log.info("Intercepting token: $token")
    val authorities = if (token != null) authRequestService.getUserAuthorities(token)
      else throw AuthorizationException("Anonymous access is restricted to this endpoint")
    when (authorities.any { role -> authRequired.role == role }) {
      false -> throw AuthorizationException("User does not have required role: ${authRequired.role}")
      true -> log.info("User successfully authenticated with role: ${authRequired.role}")
    }
  }

}