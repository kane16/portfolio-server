package pl.delukesoft.portfolioserver.domain.resume.skill.domain

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

class SkillDomainValidator : Validator<SkillDomain>() {

  override fun validate(value: SkillDomain): ValidationResult {
    val results = listOf(
      validationFunc(value, ::notEmptyNamePredicate, "Skill name must not be empty"),
      validationFunc(value, ::minThreeSignsNamePredicate, "Skill name must be at least 3 characters long")
    )

    return if (results.all { it.isValid }) ValidationResult.build() else ValidationResult.build(results.flatMap { it.errors })
  }

  override fun validateList(values: List<SkillDomain>): ValidationResult {
    val results = values.map { validate(it) } + listOf(
      skillDomainsNotDuplicatedInSkill(values),
    )
    return if (results.all { it.isValid }) ValidationResult.build() else ValidationResult.build(results.flatMap { it.errors })
  }

  private fun notEmptyNamePredicate(skillDomain: SkillDomain): Boolean =
    skillDomain.name.trim().isNotEmpty()

  private fun minThreeSignsNamePredicate(skillDomain: SkillDomain): Boolean =
    skillDomain.name.trim().length >= 3

  private fun skillDomainsNotDuplicatedInSkill(skillDomains: List<SkillDomain>): ValidationResult =
    if (skillDomains.groupBy { it.name.trim().lowercase() }.values.all { it.count() <= 1 }) ValidationResult.build()
    else ValidationResult.build("Skill domain cannot be duplicated")

}