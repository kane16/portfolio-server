package pl.delukesoft.portfolioserver.utility.exception

import org.springframework.http.HttpStatus

class InvalidMappingException(message: String): LoggableResponseStatusException(HttpStatus.NOT_FOUND, message) {
}