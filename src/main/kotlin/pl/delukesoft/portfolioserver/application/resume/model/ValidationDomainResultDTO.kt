package pl.delukesoft.portfolioserver.application.resume.model

import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus

data class ValidationDomainResultDTO(
  val validationStatus: ValidationStatus,
  val domain: ValidationDomainDTO,
  val errors: List<String> = emptyList()
)