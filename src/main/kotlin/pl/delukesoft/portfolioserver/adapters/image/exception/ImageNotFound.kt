package pl.delukesoft.blog.image.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class ImageNotFound(exc: Exception? = null): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Image not found", exc) {
}