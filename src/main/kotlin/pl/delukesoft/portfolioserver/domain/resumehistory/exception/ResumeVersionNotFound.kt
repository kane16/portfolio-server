package pl.delukesoft.portfolioserver.domain.resumehistory.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class ResumeVersionNotFound(exc: Exception? = null) :
  LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Resume Version not found", exc)