package pl.delukesoft.portfolioserver.domain.resume.skill

import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus
import pl.delukesoft.portfolioserver.domain.validation.Validator

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
        duplicateSkillValidation(values),
      ) + values.map { skillDomainValidator.validateList(it.domains) } + values.map { validate(it) }

    return ValidationResult(
      skillsValidations.all { it.isValid },
      skillsValidations.firstOrNull { it.validationStatus == ValidationStatus.INVALID }?.validationStatus
        ?: ValidationStatus.VALID,
      skillsValidations.flatMap { it.errors }
    )
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