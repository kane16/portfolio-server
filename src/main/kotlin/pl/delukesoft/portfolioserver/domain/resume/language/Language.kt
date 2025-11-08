package pl.delukesoft.portfolioserver.domain.resume.language

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.WithConstraints

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
