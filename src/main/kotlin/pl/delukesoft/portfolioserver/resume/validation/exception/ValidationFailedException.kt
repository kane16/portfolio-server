package pl.delukesoft.portfolioserver.resume.validation.exception

import org.springframework.http.HttpStatus
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException
import pl.delukesoft.portfolioserver.resume.validation.DomainValidationResult

class ValidationFailedException(val validationResults: List<DomainValidationResult>) :
  LoggableResponseStatusException(
    HttpStatus.BAD_REQUEST,
    "Validation failed: ${validationResults.joinToString(", ") { it.errors.first() }}"
  )