package pl.delukesoft.portfolioserver.resume.experience.business.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class BusinessNotFound(name: String): LoggableResponseStatusException(HttpStatus.NOT_FOUND, "Business with name $name not found") {
}