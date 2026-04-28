package pl.delukesoft.portfolioserver.resume.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class ResumePublicationFailed(message: String) : LoggableResponseStatusException(HttpStatus.BAD_REQUEST, message)