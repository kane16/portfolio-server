package pl.delukesoft.portfolioserver.adapters.auth

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidTokenException(token: String) : ResponseStatusException(HttpStatus.UNAUTHORIZED)
