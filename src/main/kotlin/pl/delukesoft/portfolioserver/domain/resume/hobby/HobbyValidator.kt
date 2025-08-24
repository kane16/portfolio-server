package pl.delukesoft.portfolioserver.domain.resume.hobby

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

class HobbyValidator : Validator<Hobby>() {

  override fun validate(value: Hobby): ValidationResult {
    val validationResults: List<ValidationResult> = listOf(
      validationFunc(value, ::nameWithoutHeadingAndTrailingSpacesPredicate, "Hobby name must not contain spaces"),
      validationFunc(value, ::nameCapitalizedPredicate, "Hobby name must be capitalized"),
      validationFunc(value, ::nameNotEmptyPredicate, "Hobby name must not be empty")
    )

    return if (validationResults.any { !it.isValid }) {
      ValidationResult.build(validationResults.map { it.errors }.flatten())
    } else ValidationResult.build()
  }

  override fun validateList(values: List<Hobby>): ValidationResult {
    val validationResults = values.map { validate(it) } + listOf()

    return if (validationResults.any { !it.isValid }) {
      ValidationResult.build(validationResults.map { it.errors }.flatten())
    } else ValidationResult.build()
  }

  private fun nameWithoutHeadingAndTrailingSpacesPredicate(hobby: Hobby): Boolean =
    hobby.name.trim().length == hobby.name.length

  private fun nameCapitalizedPredicate(hobby: Hobby): Boolean =
    hobby.name.trim().isEmpty() || (hobby.name.trim().first().isUpperCase() && hobby.name.trim().drop(1)
      .all { it.isLowerCase() })

  private fun nameNotEmptyPredicate(hobby: Hobby): Boolean =
    hobby.name.trim().isNotEmpty()

}