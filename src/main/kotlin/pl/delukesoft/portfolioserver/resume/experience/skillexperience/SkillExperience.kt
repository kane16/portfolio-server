package pl.delukesoft.portfolioserver.resume.experience.skillexperience

import pl.delukesoft.portfolioserver.resume.skill.Skill
import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.WithConstraints

data class SkillExperience(
  val skill: Skill,
  val level: Int,
  val detail: String
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.experience.skill.detail", detail)
    )
  }

}