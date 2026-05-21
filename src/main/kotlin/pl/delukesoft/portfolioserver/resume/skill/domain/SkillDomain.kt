package pl.delukesoft.portfolioserver.resume.skill.domain

import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.WithConstraints

data class SkillDomain(
  val name: String
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.skill.domain.name", name)
    )
  }

}