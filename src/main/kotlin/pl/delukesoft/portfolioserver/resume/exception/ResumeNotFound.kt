package pl.delukesoft.portfolioserver.resume.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class ResumeNotFound(exc: Exception? = null): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "CV not found", exc) {
}