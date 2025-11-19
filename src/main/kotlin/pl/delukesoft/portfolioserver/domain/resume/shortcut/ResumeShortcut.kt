package pl.delukesoft.portfolioserver.domain.resume.shortcut

import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.adapters.image.Image
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.WithConstraints

data class ResumeShortcut(
  val user: User,
  val title: String,
  val summary: String,
  val image: Image? = null,
  val contact: ContactInfo? = null,
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.shortcut.title", title),
      validationFunc("resume.shortcut.summary", summary)
    )
  }

}