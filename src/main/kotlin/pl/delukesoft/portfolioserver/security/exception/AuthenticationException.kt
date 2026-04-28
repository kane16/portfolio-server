package pl.delukesoft.portfolioserver.security.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class AuthenticationException(requiredRole: String) : LoggableResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. Required role: $requiredRole") {
}