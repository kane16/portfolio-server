package pl.delukesoft.portfolioserver.application.resume.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class DomainNotFound(domain: String) : LoggableResponseStatusException(
  HttpStatus.BAD_REQUEST,
  "Domain with name $domain not found"
)