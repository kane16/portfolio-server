package pl.delukesoft.portfolioserver.resume.language

import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.WithConstraints

data class Language(
  val id: Long? = null,
  val name: String,
  val level: LanguageLevel,
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.language.name", name)
    )
  }

}
