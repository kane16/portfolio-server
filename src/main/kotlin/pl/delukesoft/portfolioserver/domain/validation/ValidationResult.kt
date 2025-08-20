package pl.delukesoft.portfolioserver.domain.validation

data class ValidationResult(
  val isValid: Boolean = false,
  val validationStatus: ValidationStatus = ValidationStatus.NOT_VALIDATED,
  val errors: List<String> = emptyList()
) {

  companion object {

    fun build(errors: List<String>): ValidationResult = ValidationResult(false, ValidationStatus.INVALID, errors)
    fun build(error: String): ValidationResult = ValidationResult(false, ValidationStatus.INVALID, listOf(error))
    fun build(): ValidationResult = ValidationResult(true, ValidationStatus.VALID, emptyList())

  }

}
