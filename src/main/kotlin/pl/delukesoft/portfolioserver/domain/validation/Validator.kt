package pl.delukesoft.portfolioserver.domain.validation

abstract class Validator<T> {

  abstract fun validate(value: T): ValidationResult

}