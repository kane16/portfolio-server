package pl.delukesoft.portfolioserver.platform.exception

import org.springframework.http.HttpStatus

class InvalidMappingException(message: String): LoggableResponseStatusException(HttpStatus.NOT_FOUND, message) {
}