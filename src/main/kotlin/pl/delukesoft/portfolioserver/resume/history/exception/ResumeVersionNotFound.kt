package pl.delukesoft.portfolioserver.resume.history.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class ResumeVersionNotFound(exc: Exception? = null) :
  LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Resume Version not found", exc)