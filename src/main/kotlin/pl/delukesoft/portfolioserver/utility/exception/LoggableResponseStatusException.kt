package pl.delukesoft.portfolioserver.utility.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class LoggableResponseStatusException(status: HttpStatus, message: String, exc: Exception? = null): ResponseStatusException(status, message) {

  init {
    if(exc != null) {
      logger.error(message, exc)
    } else {
      logger.error(message)
    }
  }

  companion object {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

  }

}