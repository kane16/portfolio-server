package pl.delukesoft.portfolioserver.domain.resume.skill.domain

import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

class SkillDomainValidator(
  private val constraintService: ConstraintService
) : Validator<SkillDomain>() {

  override fun validate(value: SkillDomain): ValidationResult {
    val results = value.validateConstraintPaths(constraintService::validateConstraint)

    return if (results.all { it.isValid }) ValidationResult.build() else ValidationResult.build(results.flatMap { it.errors })
  }

  override fun validateList(values: List<SkillDomain>): ValidationResult {
    val results = values.map { validate(it) } + listOf(
      skillDomainsNotDuplicatedInSkill(values),
    )
    return if (results.all { it.isValid }) ValidationResult.build() else ValidationResult.build(results.flatMap { it.errors })
  }

  private fun skillDomainsNotDuplicatedInSkill(skillDomains: List<SkillDomain>): ValidationResult =
    if (skillDomains.groupBy { it.name.trim().lowercase() }.values.all { it.count() <= 1 }) ValidationResult.build()
    else ValidationResult.build("Skill domain cannot be duplicated")

}