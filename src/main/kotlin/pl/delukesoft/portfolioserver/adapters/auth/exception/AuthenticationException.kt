package pl.delukesoft.portfolioserver.adapters.auth.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class AuthenticationException(requiredRole: String) : LoggableResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. Required role: $requiredRole") {
}