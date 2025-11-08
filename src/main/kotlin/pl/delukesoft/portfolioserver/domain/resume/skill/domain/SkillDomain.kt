package pl.delukesoft.portfolioserver.domain.resume.skill.domain

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.WithConstraints

data class SkillDomain(
  val name: String
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.skill.domain.name", name)
    )
  }

}