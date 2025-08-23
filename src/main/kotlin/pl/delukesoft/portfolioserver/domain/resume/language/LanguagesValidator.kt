package pl.delukesoft.portfolioserver.domain.resume.language

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus
import pl.delukesoft.portfolioserver.domain.validation.Validator

class LanguagesValidator : Validator<Language>() {

  override fun validate(value: Language): ValidationResult {
    val languagesValidations = listOf(
      validationFunc(
        value,
        ::hasAtLeastThreeCharactersForLanguageName,
        "Language name must be at least 3 characters long"
      ),
      validationFunc(
        value,
        ::trimmedLanguageName,
        "Language name must not contain leading or trailing spaces"
      ),
      validationFunc(value, ::capitalizedLanguageName, "Language name must be capitalized")
    )

    return ValidationResult(
      languagesValidations.all { it.isValid },
      languagesValidations.firstOrNull { it.validationStatus == ValidationStatus.INVALID }?.validationStatus
        ?: ValidationStatus.VALID,
      languagesValidations.flatMap { it.errors }
    )
  }

  override fun validateList(values: List<Language>): ValidationResult {
    val languagesValidations = listOf(
      atLeastTwoLanguagesValidation(values),
      validationListFunc(
        values,
        ::hasAtLeastThreeCharactersForLanguageName,
        "Language name must be at least 3 characters long"
      ),
      normalizedLanguageNamesValidator(values),
      validationListFunc(
        values,
        ::trimmedLanguageName,
        "Language name must not contain leading or trailing spaces"
      ),
      validationListFunc(values, ::capitalizedLanguageName, "Language name must be capitalized")
    )

    return ValidationResult(
      languagesValidations.all { it.isValid },
      languagesValidations.firstOrNull { it.validationStatus == ValidationStatus.INVALID }?.validationStatus
        ?: ValidationStatus.VALID,
      languagesValidations.flatMap { it.errors }
    )
  }

  private fun atLeastTwoLanguagesValidation(languages: List<Language>): ValidationResult {
    return if (languages.count() < 2) {
      ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf("At least two languages are required")
      )
    } else {
      ValidationResult(true, ValidationStatus.VALID, emptyList())
    }
  }

  private fun normalizedLanguageNamesValidator(languages: List<Language>): ValidationResult =
    languages.groupBy { it.name.trim().lowercase() }.values.filter {
      it.count() > 1
    }.map {
      ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf("Language cannot be duplicated")
      )
    }.firstOrNull() ?: ValidationResult(true, ValidationStatus.VALID, emptyList())

  private fun trimmedLanguageName(language: Language): Boolean {
    return language.name.trim() == language.name
  }

  private fun capitalizedLanguageName(language: Language): Boolean {
    return language.name.trim().firstOrNull { it.isUpperCase() } != null
  }

  private fun hasAtLeastThreeCharactersForLanguageName(language: Language): Boolean {
    return language.name.trim().length > 3
  }

}