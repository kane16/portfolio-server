package pl.delukesoft.portfolioserver.resume.skill

import pl.delukesoft.portfolioserver.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.WithConstraints

data class Skill(
  val name: String,
  val level: Int,
  val description: String? = null,
  val domains: List<SkillDomain> = emptyList()
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.skill.name", name),
      validationFunc("resume.skill.description", description)
    )
  }

}
