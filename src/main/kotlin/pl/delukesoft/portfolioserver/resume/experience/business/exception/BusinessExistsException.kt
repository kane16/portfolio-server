package pl.delukesoft.portfolioserver.resume.experience.business.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class BusinessExistsException(name: String) :
  LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Business with name $name already exists")