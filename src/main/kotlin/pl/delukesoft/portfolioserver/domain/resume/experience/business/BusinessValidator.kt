package pl.delukesoft.portfolioserver.domain.resume.experience.business

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class BusinessValidator : Validator<Business>() {

  override fun validate(value: Business): ValidationResult {
    val validationResults: List<ValidationResult> = listOf(
      validationFunc(value, ::nameCapitalizedPredicate, "Business name must be capitalized"),
      validationFunc(value, ::nameAtLeastThreeCharactersPredicate, "Business name must be at least 3 letters"),
      validationFunc(
        value,
        ::nameMustContainLettersOnlyPredicate,
        "Business name must contain only letters (no spaces)"
      )
    )

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

  private fun nameCapitalizedPredicate(business: Business): Boolean =
    business.name.trim().first().isUpperCase() && business.name.trim().drop(1).all { it.isLowerCase() }

  private fun nameAtLeastThreeCharactersPredicate(business: Business): Boolean =
    business.name.trim().length >= 3

  private fun nameMustContainLettersOnlyPredicate(business: Business): Boolean =
    business.name.matches(Regex("[a-zA-Z]+"))

}