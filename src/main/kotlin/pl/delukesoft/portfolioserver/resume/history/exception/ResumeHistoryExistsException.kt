package pl.delukesoft.portfolioserver.resume.history.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class ResumeHistoryExistsException(username: String): LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Resume History already exists for user: $username") {
}