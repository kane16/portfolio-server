package pl.delukesoft.portfolioserver.author.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class AuthorNotFound : LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")