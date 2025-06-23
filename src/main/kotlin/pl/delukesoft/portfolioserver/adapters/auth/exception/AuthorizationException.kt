package pl.delukesoft.portfolioserver.adapters.auth.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class AuthorizationException(reason: String, exc: Exception? = null) : LoggableResponseStatusException(HttpStatus.UNAUTHORIZED, reason, exc) {
}