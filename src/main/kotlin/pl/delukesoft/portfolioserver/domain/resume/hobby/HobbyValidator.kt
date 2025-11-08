package pl.delukesoft.portfolioserver.domain.resume.hobby

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class HobbyValidator : Validator<Hobby>() {

  override fun validate(value: Hobby): ValidationResult {
    val validationResults: List<ValidationResult> = listOf(
      validationFunc(value, ::nameWithoutHeadingAndTrailingSpacesPredicate, "Hobby name must not contain spaces"),
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

  private fun nameNotEmptyPredicate(hobby: Hobby): Boolean =
    hobby.name.trim().isNotEmpty()

}