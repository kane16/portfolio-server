package pl.delukesoft.portfolioserver.domain.resume.language

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus
import pl.delukesoft.portfolioserver.domain.validation.Validator

class LanguagesValidator : Validator<List<Language>>() {

  override fun validate(value: List<Language>): ValidationResult {
    val languagesValidations = listOf(
      atLeastTwoLanguagesValidation(value),
      languagesValidation(
        value,
        ::hasAtLeastThreeCharactersForLanguageName,
        "Language name must be at least 3 characters long"
      ),
      normalizedLanguageNamesValidator(value),
      languagesValidation(
        value,
        ::trimmedLanguageName,
        "Language name must not contain leading or trailing spaces"
      ),
      languagesValidation(value, ::capitalizedLanguageName, "Language name must be capitalized")
    )

    return ValidationResult(
      languagesValidations.all { it.isValid },
      languagesValidations.firstOrNull { it.validationStatus == ValidationStatus.INVALID }?.validationStatus
        ?: ValidationStatus.VALID,
      languagesValidations.flatMap { it.errors }
    )
  }

  private fun languagesValidation(
    languages: List<Language>,
    validationFunc: (language: Language) -> Boolean,
    message: String
  ): ValidationResult {
    return if (languages.any { !validationFunc(it) }) {
      ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf(message)
      )
    } else {
      ValidationResult(true, ValidationStatus.VALID, emptyList())
    }
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