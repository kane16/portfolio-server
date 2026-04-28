package pl.delukesoft.portfolioserver.security.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class UserNotFoundException(userId: Long) :
  LoggableResponseStatusException(HttpStatus.NOT_FOUND, "User with id $userId not found")