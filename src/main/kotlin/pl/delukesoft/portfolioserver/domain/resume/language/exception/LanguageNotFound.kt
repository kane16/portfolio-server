package pl.delukesoft.blog.image.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class LanguageNotFound(name: String): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Language with name $name not found") {
}