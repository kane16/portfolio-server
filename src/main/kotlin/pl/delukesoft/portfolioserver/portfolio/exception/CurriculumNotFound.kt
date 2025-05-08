package pl.delukesoft.blog.image.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.exception.LoggableResponseStatusException

class CurriculumNotFound(exc: Exception? = null): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "CV not found", exc) {
}