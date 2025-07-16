package pl.delukesoft.portfolioserver.domain.resumehistory.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class ResumeHistoryNotFound(exc: Exception? = null): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Resume History not found", exc) {
}