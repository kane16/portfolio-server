package pl.delukesoft.portfolioserver.domain.resume.shortcut.contact

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class ContactInfoValidator(
  private val constraintService: ConstraintService
) : Validator<ContactInfo>() {

  override fun validate(value: ContactInfo): ValidationResult {
    val validationResults: List<ValidationResult> = value.validateConstraintPaths(constraintService::validateConstraint)

    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

  override fun validateList(values: List<ContactInfo>): ValidationResult {
    val validationResults = values.map { validate(it) }
    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

}