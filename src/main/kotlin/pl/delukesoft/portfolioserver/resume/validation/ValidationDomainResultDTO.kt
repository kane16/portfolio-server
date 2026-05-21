package pl.delukesoft.portfolioserver.resume.validation

data class ValidationDomainResultDTO(
  val validationStatus: ValidationStatus,
  val domain: ValidationDomainDTO,
  val errors: List<String> = emptyList()
)