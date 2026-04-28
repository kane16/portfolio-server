package pl.delukesoft.portfolioserver.resume.experience.skillexperience

import pl.delukesoft.portfolioserver.resume.validation.constraint.ConstraintService
import pl.delukesoft.portfolioserver.resume.skill.Skill
import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.Validator

class SkillExperienceValidator(
  private val skillValidator: Validator<Skill>,
  private val constraintService: ConstraintService
) : Validator<SkillExperience>() {

  override fun validate(value: SkillExperience): ValidationResult {
    val validationResults: List<ValidationResult> = listOf(
      validationFunc(value, ::levelInBounds, "Experience Skill Level must be between 1 and 5"),
      skillValidator.validate(value.skill)
    ) + value.validateConstraintPaths(constraintService::validateConstraint)

    return if (validationResults.any { !it.isValid }) {
      ValidationResult.build(validationResults.map { it.errors }.flatten())
    } else ValidationResult.build()
  }

  override fun validateList(values: List<SkillExperience>): ValidationResult {
    val validationResults = values.map { validate(it) } + listOf()

    return if (validationResults.any { !it.isValid }) {
      ValidationResult.build(validationResults.map { it.errors }.flatten())
    } else ValidationResult.build()
  }

  private fun levelInBounds(skillExperience: SkillExperience): Boolean =
    skillExperience.level in 1..5

}