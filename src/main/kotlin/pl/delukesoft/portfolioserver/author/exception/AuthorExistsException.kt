package pl.delukesoft.portfolioserver.author.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class AuthorExistsException : LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Author already exists")