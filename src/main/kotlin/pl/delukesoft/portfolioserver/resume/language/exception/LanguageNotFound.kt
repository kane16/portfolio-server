package pl.delukesoft.portfolioserver.resume.language.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class LanguageNotFound(name: String): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Language with name $name not found") {
}