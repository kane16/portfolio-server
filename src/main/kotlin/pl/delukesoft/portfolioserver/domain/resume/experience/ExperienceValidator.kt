package pl.delukesoft.portfolioserver.domain.resume.experience

import org.springframework.beans.factory.annotation.Qualifier
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.timespan.ConsecutiveTimeframeValidator
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

class ExperienceValidator(
  private val consecutiveTimeframeValidator: ConsecutiveTimeframeValidator,
  @Qualifier("businessValidator") private val businessValidator: Validator<Business>,
  @Qualifier("skillExperienceValidator") private val skillExperienceValidator: Validator<SkillExperience>
) : Validator<Experience>() {

  override fun validate(value: Experience): ValidationResult {
    val validationResults: List<ValidationResult> = listOf(
      validationFunc(value, ::summaryInBounds, "Experience summary must be between 10 and 100 characters"),
      validationFunc(value, ::descriptionInBounds, "Experience description must be between 10 and 300 characters"),
      validationFunc(value, ::positionInBounds, "Experience position must be between 6 and 30 characters"),
      businessValidator.validate(value.business),
      skillExperienceValidator.validateList(value.skills)
    )

    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

  override fun validateList(values: List<Experience>): ValidationResult {
    val validationResults = values.map { validate(it) } + listOf(
      consecutiveTimeframeValidator.validateList(values.map { it.timeframe })
    )

    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

  private fun summaryInBounds(experience: Experience): Boolean =
    experience.summary.length in 10..100

  private fun descriptionInBounds(experience: Experience): Boolean =
    if (experience.description != null) experience.description.length in 10..300 else true

  private fun positionInBounds(experience: Experience): Boolean =
    experience.position.length in 6..30

}