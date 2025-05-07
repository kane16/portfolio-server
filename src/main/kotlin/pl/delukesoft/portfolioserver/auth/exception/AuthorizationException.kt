package pl.delukesoft.portfolioserver.auth.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.exception.LoggableResponseStatusException

class AuthorizationException(reason: String, exc: Exception? = null) : LoggableResponseStatusException(HttpStatus.UNAUTHORIZED, reason, exc) {
}