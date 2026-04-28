package pl.delukesoft.portfolioserver.security.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidTokenException(reason: String) : ResponseStatusException(HttpStatus.UNAUTHORIZED, reason)
