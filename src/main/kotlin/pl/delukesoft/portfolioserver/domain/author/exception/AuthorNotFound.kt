package pl.delukesoft.portfolioserver.domain.author.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class AuthorNotFound() : LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")