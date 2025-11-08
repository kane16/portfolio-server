package pl.delukesoft.portfolioserver.domain.resume.experience

import org.springframework.beans.factory.annotation.Qualifier
import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

class ExperienceValidator(
  val timeframeValidator: TimeframeValidator,
  @Qualifier("businessValidator") private val businessValidator: Validator<Business>,
  @Qualifier("skillExperienceValidator") private val skillExperienceValidator: Validator<SkillExperience>,
  private val constraintService: ConstraintService
) : Validator<Experience>() {

  override fun validate(value: Experience): ValidationResult {
    val validationResults: List<ValidationResult> = listOf(
      businessValidator.validate(value.business),
      skillExperienceValidator.validateList(value.skills)
    ) + value.validateConstraintPaths(constraintService::validateConstraint)

    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

  override fun validateList(values: List<Experience>): ValidationResult {
    val validationResults = values.map { validate(it) } + listOf(
      timeframeValidator.validateList(values.map { it.timeframe })
    )

    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

}