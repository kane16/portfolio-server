package pl.delukesoft.blog.image.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class ResumeExistsException(id: Long): LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Resume with id: $id already exists") {
}