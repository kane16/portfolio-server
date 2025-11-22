package pl.delukesoft.portfolioserver.domain.resume.shortcut.contact

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.WithConstraints

data class ContactInfo(
  val email: String,
  val phone: String,
  val location: String,
  val linkedin: String,
  val github: String,
  val timezone: String
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.shortcut.contact.email", email),
      validationFunc("resume.shortcut.contact.phone", phone),
      validationFunc("resume.shortcut.contact.location", location),
      validationFunc("resume.shortcut.contact.linkedin", linkedin),
      validationFunc("resume.shortcut.contact.github", github),
      validationFunc("resume.shortcut.contact.timezone", timezone)
    )
  }


}