package pl.delukesoft.portfolioserver.resume.skill

import pl.delukesoft.portfolioserver.resume.validation.constraint.ConstraintService
import pl.delukesoft.portfolioserver.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.ValidationStatus
import pl.delukesoft.portfolioserver.resume.validation.Validator

class SkillValidator(
  private val skillDomainValidator: Validator<SkillDomain>,
  private val constraintService: ConstraintService
) : Validator<Skill>() {

  override fun validate(value: Skill): ValidationResult {
    val skillsValidations =
      listOf(
        validationFunc(value, ::skillLevelValid, "Skill level must be between 1 and 5"),
        skillDomainValidator.validateList(value.domains)
      ) + value.validateConstraintPaths(constraintService::validateConstraint)

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
        duplicateSkillValidation(values),
      ) + values.map { skillDomainValidator.validateList(it.domains) } + values.map { validate(it) }

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

}