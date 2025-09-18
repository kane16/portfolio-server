package pl.delukesoft.portfolioserver.domain.validation

class ObligatoryElementValidatorDecorator<T>(
  val defaultValidator: Validator<T>
) : Validator<T>() {

  override fun validate(value: T): ValidationResult {
    return defaultValidator.validate(value)
  }

  override fun validateList(values: List<T>): ValidationResult {
    if (values.isEmpty()) {
      return ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf("List must not be empty")
      )
    }
    return defaultValidator.validateList(values)
  }

}