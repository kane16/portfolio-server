package pl.delukesoft.portfolioserver.resume.validation.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

class DomainNotFound(domain: String) : LoggableResponseStatusException(
  HttpStatus.BAD_REQUEST,
  "Domain with name $domain not found"
)