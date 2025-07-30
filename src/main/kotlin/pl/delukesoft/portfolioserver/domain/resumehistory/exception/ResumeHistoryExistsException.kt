package pl.delukesoft.portfolioserver.domain.resumehistory.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class ResumeHistoryExistsException(username: String): LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Resume History already exists for user: $username") {
}