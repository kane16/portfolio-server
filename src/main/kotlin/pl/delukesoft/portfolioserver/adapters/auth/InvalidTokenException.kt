package pl.delukesoft.portfolioserver.adapters.auth

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidTokenException(reason: String) : ResponseStatusException(HttpStatus.UNAUTHORIZED, reason)
