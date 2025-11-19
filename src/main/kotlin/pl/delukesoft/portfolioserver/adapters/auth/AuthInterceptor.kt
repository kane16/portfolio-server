package pl.delukesoft.portfolioserver.adapters.auth

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.exception.AuthenticationException
import pl.delukesoft.portfolioserver.adapters.auth.exception.AuthorizationException
import pl.delukesoft.portfolioserver.domain.author.AuthorService

@Aspect
@Component
class AuthInterceptor(
  private val userContext: UserContext,
  private val authRequestService: AuthRequestService,
  private val authorService: AuthorService
) {

  private val log = LoggerFactory.getLogger(AuthInterceptor::class.java)

  @Before("@annotation(authRequired) && args(.., token)", argNames = "authRequired, token")
  fun interceptToken(authRequired: AuthRequired, token: String? = null) {
    if (token == null && authRequired.anonymousAllowed) {
      return
    }
    log.info("Intercepting token: $token")
    val user = if (token != null) authRequestService.getUser(token)
      else throw AuthorizationException("Anonymous access is restricted to this endpoint")
    val author = authorService.getAuthor(user)
    val roles = if (author != null) user.roles + "ROLE_CANDIDATE" else user.roles
    when (roles.any { role -> authRequired.roles.contains(role) } || user.roles.contains("ROLE_ADMIN")) {
      false -> throw AuthenticationException(authRequired.roles.joinToString(", "))
      true -> {
        userContext.user = user
        userContext.author = author
        log.info("User successfully authenticated with roles: ${user.roles.joinToString(", ")}")
      }
    }
  }

}