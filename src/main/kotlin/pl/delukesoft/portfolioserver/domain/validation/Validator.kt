package pl.delukesoft.portfolioserver.domain.validation

abstract class Validator<T> {

  abstract fun validate(value: T): ValidationResult

  abstract fun validateList(values: List<T>): ValidationResult

  protected fun validationListFunc(
    values: List<T>,
    validationPredicate: (T) -> Boolean,
    message: String
  ): ValidationResult {
    return if (values.any { !validationPredicate(it) }) {
      ValidationResult.build(message)
    } else ValidationResult.build()
  }

  protected fun validationFunc(
    value: T,
    validationPredicate: (T) -> Boolean,
    message: String
  ): ValidationResult {
    return if (!validationPredicate(value)) {
      ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf(message)
      )
    } else {
      ValidationResult(
        true,
        ValidationStatus.VALID,
        emptyList()
      )
    }
  }

}