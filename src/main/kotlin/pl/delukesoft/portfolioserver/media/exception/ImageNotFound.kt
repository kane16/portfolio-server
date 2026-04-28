package pl.delukesoft.portfolioserver.media.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class ImageNotFound(exc: Exception? = null): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Image not found", exc) {
}