package pl.delukesoft.portfolioserver.domain.resume.skill

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainValidator
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class SkillValidator(
  private val skillDomainValidator: SkillDomainValidator
) : Validator<Skill>() {

  override fun validate(value: Skill): ValidationResult {
    val skillsValidations =
      listOf(
        validationFunc(value, ::skillLevelValid, "Skill level must be between 1 and 5"),
        validationFunc(value, ::skillNameNotEmpty, "Skill name must be at least 1 character"),
        skillDomainValidator.validateList(value.domains)
      )

    return ValidationResult(
      skillsValidations.all { it.isValid },
      skillsValidations.firstOrNull { it.validationStatus == ValidationStatus.INVALID }?.validationStatus
        ?: ValidationStatus.VALID,
      skillsValidations.flatMap { it.errors }
    )
  }

  override fun validateList(values: List<Skill>): ValidationResult {
    val skillsValidations =
      listOf(
        emptySkillsValidation(values),
        validationListFunc(values, ::skillLevelValid, "Skill level must be between 1 and 5"),
        validationListFunc(values, ::skillNameNotEmpty, "Skill name must be at least 1 character"),
        duplicateSkillValidation(values),
      ) + values.map { skillDomainValidator.validateList(it.domains) }

    return ValidationResult(
      skillsValidations.all { it.isValid },
      skillsValidations.firstOrNull { it.validationStatus == ValidationStatus.INVALID }?.validationStatus
        ?: ValidationStatus.VALID,
      skillsValidations.flatMap { it.errors }
    )
  }

  private fun emptySkillsValidation(skills: List<Skill>): ValidationResult {
    if (skills.isEmpty()) {
      return ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf("At least one skill is required")
      )
    }
    return ValidationResult(true, ValidationStatus.VALID, emptyList())
  }

  private fun duplicateSkillValidation(skills: List<Skill>): ValidationResult {
    return skills.groupBy { it.name.trim().lowercase() }.values.filter {
      it.count() > 1
    }.map {
      ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf("Skill cannot be duplicated")
      )
    }.firstOrNull() ?: ValidationResult(true, ValidationStatus.VALID, emptyList())
  }

  private fun skillLevelValid(skill: Skill): Boolean {
    return skill.level in 1..5
  }

  private fun skillNameNotEmpty(skill: Skill): Boolean {
    return skill.name.trim().isNotEmpty()
  }

}