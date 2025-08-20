package pl.delukesoft.portfolioserver.domain.resume.skill

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus
import pl.delukesoft.portfolioserver.domain.validation.Validator

class SkillsValidator : Validator<List<Skill>>() {

  override fun validate(value: List<Skill>): ValidationResult {
    val skillsValidations =
      listOf(
        emptySkillsValidation(value),
        skillsValidation(value, ::skillLevelValid, "Skill level must be between 1 and 5"),
        skillsValidation(value, ::skillNameNotEmpty, "Skill name must be at least 1 character"),
        duplicateSkillValidation(value),
        duplicateSkillDomainValidation(value),
      )

    return ValidationResult(
      skillsValidations.all { it.isValid },
      skillsValidations.firstOrNull { it.validationStatus == ValidationStatus.INVALID }?.validationStatus
        ?: ValidationStatus.VALID,
      skillsValidations.flatMap { it.errors }
    )
  }

  private fun skillsValidation(
    skills: List<Skill>,
    validationFunc: (skill: Skill) -> Boolean,
    message: String
  ): ValidationResult {
    return if (skills.any { !validationFunc(it) }) {
      ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf(message)
      )
    } else {
      ValidationResult(
        true,
        ValidationStatus.VALID,
        emptyList()
      )
    }
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

  private fun duplicateSkillDomainValidation(skills: List<Skill>): ValidationResult {
    return skills.filter { skillDomainDuplicatedInSkill(it) }.map {
      ValidationResult(
        false,
        ValidationStatus.INVALID,
        listOf("Skill domain cannot be duplicated")
      )
    }.firstOrNull() ?: ValidationResult(true, ValidationStatus.VALID, emptyList())
  }

  private fun skillDomainDuplicatedInSkill(skill: Skill): Boolean {
    return skill.domains.groupBy { it.name.trim().lowercase() }.values.any { it.count() > 1 }
  }

  private fun skillLevelValid(skill: Skill): Boolean {
    return skill.level in 1..5
  }

  private fun skillNameNotEmpty(skill: Skill): Boolean {
    return skill.name.trim().isNotEmpty()
  }

}