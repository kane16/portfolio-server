package pl.delukesoft.portfolioserver.domain.resume.shortcut

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class ShortcutValidator: Validator<ResumeShortcut>() {
  override fun validate(value: ResumeShortcut): ValidationResult {
    TODO("Not yet implemented")
  }

  override fun validateList(values: List<ResumeShortcut>): ValidationResult {
    TODO("Not yet implemented")
  }
}