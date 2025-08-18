package pl.delukesoft.portfolioserver.domain.validation

abstract class SimpleValidator<T> : Validator<T>() {

  override fun validate(value: T): ValidationResult {
    return validateSelf(value)
  }

  protected abstract fun validateSelf(value: T): ValidationResult

}