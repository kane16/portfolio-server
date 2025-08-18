package pl.delukesoft.portfolioserver.domain.validation

data class ValidationResult(
  val isValid: Boolean = false,
  val validationStatus: ValidationStatus = ValidationStatus.NOT_VALIDATED,
  val errors: List<String> = emptyList()
)
