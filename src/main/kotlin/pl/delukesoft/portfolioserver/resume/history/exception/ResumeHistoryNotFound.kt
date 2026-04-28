package pl.delukesoft.portfolioserver.resume.history.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class ResumeHistoryNotFound(exc: Exception? = null): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Resume History not found", exc) {
}