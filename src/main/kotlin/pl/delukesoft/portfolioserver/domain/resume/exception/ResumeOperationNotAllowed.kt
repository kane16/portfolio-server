package pl.delukesoft.blog.image.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class ResumeOperationNotAllowed(message: String) : LoggableResponseStatusException(HttpStatus.BAD_REQUEST, message)