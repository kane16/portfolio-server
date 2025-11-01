package pl.delukesoft.portfolioserver.domain.resume.shortcut

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class ResumeShortcutValidator : Validator<ResumeShortcut>() {

  override fun validate(value: ResumeShortcut): ValidationResult {
    val validationResults: List<ValidationResult> = listOf(
      validationFunc(value, ::titleLengthInBounds, "Title length must be between 5 and 30"),
      validationFunc(value, ::summaryLengthInBounds, "Summary length must be between 30 and 1000")
    )

    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

  override fun validateList(values: List<ResumeShortcut>): ValidationResult {
    val validationResults = values.map { validate(it) }
    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

  private fun titleLengthInBounds(shortcut: ResumeShortcut): Boolean =
    shortcut.title.length in 5..30

  private fun summaryLengthInBounds(shortcut: ResumeShortcut): Boolean =
    shortcut.summary.length in 30..1000

}