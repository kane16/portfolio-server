package pl.delukesoft.portfolioserver.domain.validation.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.domain.validation.DomainValidationResult
import pl.delukesoft.portfolioserver.utility.exception.LoggableResponseStatusException

class ValidationFailedException(val validationResults: List<DomainValidationResult>) : LoggableResponseStatusException(
  HttpStatus.BAD_REQUEST,
  "Validation failed"
)