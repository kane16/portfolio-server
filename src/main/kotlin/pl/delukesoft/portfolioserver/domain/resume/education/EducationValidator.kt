package pl.delukesoft.portfolioserver.domain.resume.education

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class EducationValidator(
  @Qualifier("consecutiveTimeframeValidator") private val timeframeValidator: TimeframeValidator,
  private val constraintService: ConstraintService
) : Validator<Education>() {

  override fun validate(value: Education): ValidationResult {
    val result = listOf(
      validationFunc(value, { edu -> edu.type != null }, "Education type must not be null"),
      validationFunc(value, { edu -> edu.fieldOfStudy.isNotEmpty() }, "Field of study must not be empty"),
      validationFunc(value, { edu -> edu.institution.name.isNotEmpty() }, "Institution name must not be empty"),
      validationFunc(value, { edu -> edu.institution.country.isNotEmpty() }, "Institution country must not be empty"),
      validationFunc(value, { edu -> edu.institution.city.isNotEmpty() }, "Institution city must not be empty"),
      timeframeValidator.validate(value.timeframe)
    ) + value.validateConstraintPaths(constraintService::validateConstraint)

    return if (result.any { !it.isValid }) {
      ValidationResult.build(result.map { it.errors }.flatten())
    } else ValidationResult.build()
  }

  override fun validateList(values: List<Education>): ValidationResult {
    val validationResults = values.map { validate(it) } + listOf(
      if (values.isNotEmpty()) ValidationResult.build() else ValidationResult.build("Education list must not be empty")
    )

    return if (validationResults.all { it.isValid }) ValidationResult.build() else ValidationResult.build(
      validationResults.flatMap { it.errors })
  }

}