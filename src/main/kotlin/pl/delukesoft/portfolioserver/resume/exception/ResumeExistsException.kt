package pl.delukesoft.portfolioserver.resume.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class ResumeExistsException(id: Long): LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Resume with id: $id already exists") {
}