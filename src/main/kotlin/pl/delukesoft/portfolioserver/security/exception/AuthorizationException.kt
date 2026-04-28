package pl.delukesoft.portfolioserver.security.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class AuthorizationException(reason: String, exc: Exception? = null) : LoggableResponseStatusException(HttpStatus.UNAUTHORIZED, reason, exc) {
}