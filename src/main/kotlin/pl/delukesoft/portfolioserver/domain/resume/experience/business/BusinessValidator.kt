package pl.delukesoft.portfolioserver.domain.resume.experience.business

import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

class BusinessValidator(
  private val constraintService: ConstraintService
) : Validator<Business>() {

  override fun validate(value: Business): ValidationResult {
    val validationResults: List<ValidationResult> = value.validateConstraintPaths(constraintService::validateConstraint)

    return if (validationResults.any { !it.isValid }) {
      ValidationResult.build(validationResults.map { it.errors }.flatten())
    } else ValidationResult.build()
  }

  override fun validateList(
    values: List<Business>
  ): ValidationResult {
    val validationResults = values.map { validate(it) } + listOf()

    return if (validationResults.any { !it.isValid }) {
      ValidationResult.build(validationResults.map { it.errors }.flatten())
    } else ValidationResult.build()
  }

}