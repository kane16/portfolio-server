package pl.delukesoft.portfolioserver.domain.resume.experience.business

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.WithConstraints

data class Business(
  val name: String
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.experience.business.name", name)
    )
  }

}