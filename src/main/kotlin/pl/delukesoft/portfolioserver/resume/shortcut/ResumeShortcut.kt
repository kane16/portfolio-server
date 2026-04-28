package pl.delukesoft.portfolioserver.resume.shortcut

import pl.delukesoft.portfolioserver.security.User
import pl.delukesoft.portfolioserver.media.Image
import pl.delukesoft.portfolioserver.resume.shortcut.contact.ContactInfo
import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.WithConstraints

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