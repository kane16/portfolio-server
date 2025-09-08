package pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillValidator
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class SkillExperienceValidator(
  private val skillValidator: SkillValidator
) : Validator<SkillExperience>() {

  override fun validate(value: SkillExperience): ValidationResult {
    val validationResults: List<ValidationResult> = listOf(
      validationFunc(value, ::levelInBounds, "Experience Skill Level must be between 1 and 5"),
      validationFunc(value, ::detailAtLeastTenCharacters, "Detail must be at least 10 characters"),
      skillValidator.validate(value.skill)
    )

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

  private fun detailAtLeastTenCharacters(skillExperience: SkillExperience): Boolean =
    skillExperience.detail.trim().length >= 10

}