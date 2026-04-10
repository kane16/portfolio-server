package pl.delukesoft.portfolioserver.application.author.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class AuthorExistsException : LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Author already exists")