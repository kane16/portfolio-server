package pl.delukesoft.portfolioserver.domain.resume.hobby

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.WithConstraints

data class Hobby(
  val name: String
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.hobby.name", name)
    )
  }


}