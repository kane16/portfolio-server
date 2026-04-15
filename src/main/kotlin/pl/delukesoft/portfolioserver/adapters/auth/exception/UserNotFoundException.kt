package pl.delukesoft.portfolioserver.adapters.auth.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class UserNotFoundException(userId: Long) :
  LoggableResponseStatusException(HttpStatus.NOT_FOUND, "User with id $userId not found")