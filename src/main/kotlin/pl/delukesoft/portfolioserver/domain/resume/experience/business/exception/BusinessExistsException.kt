package pl.delukesoft.portfolioserver.domain.resume.experience.business.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class BusinessExistsException(name: String) :
  LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Business with name $name already exists")