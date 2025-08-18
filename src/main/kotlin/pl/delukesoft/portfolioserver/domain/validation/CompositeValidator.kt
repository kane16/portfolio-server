package pl.delukesoft.portfolioserver.domain.validation

abstract class CompositeValidator<T>(
  private val validators: List<Validator<T>>
) : Validator<T>() {

  override fun validate(value: T): ValidationResult {
    val validationResults = validators.map { it.validate(value) } + validateSelf(value)
    return ValidationResult(
      isValid = validationResults.all { it.isValid },
      validationStatus = markStatusBaseOnValidationResults(validationResults),
      errors = validationResults.flatMap { it.errors }
    )
  }

  private fun markStatusBaseOnValidationResults(validationResults: List<ValidationResult>): ValidationStatus {
    if (validationResults.all { it.validationStatus == ValidationStatus.VALID }) {
      return ValidationStatus.VALID
    }
    if (validationResults.any { it.validationStatus == ValidationStatus.INVALID }) {
      return ValidationStatus.INVALID
    }
    return ValidationStatus.NOT_VALIDATED
  }

  protected abstract fun validateSelf(value: T): ValidationResult

}