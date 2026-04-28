package pl.delukesoft.portfolioserver.resume.validation

import pl.delukesoft.portfolioserver.resume.validation.ValidationStatus

data class ValidationDomainResultDTO(
  val validationStatus: ValidationStatus,
  val domain: ValidationDomainDTO,
  val errors: List<String> = emptyList()
)