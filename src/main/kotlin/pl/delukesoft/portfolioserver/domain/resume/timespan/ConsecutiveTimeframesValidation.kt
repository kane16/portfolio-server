package pl.delukesoft.portfolioserver.domain.resume.timespan

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator
import java.time.LocalDate

class ConsecutiveTimeframesValidation : Validator<Timeframe>() {

  override fun validate(value: Timeframe): ValidationResult {
    val validations = listOf(
      validationFunc(value, ::startBeforeEnd, "Timeframe start must be on or before end"),
      validationFunc(value, ::startInPresentOrPast, "Timeframe cannot start in the future"),
      validationFunc(value, ::endInPresentOrPast, "Timeframe cannot end in the future"),
    )

    return if (validations.all { it.isValid }) ValidationResult.build() else ValidationResult.build(validations.flatMap { it.errors })
  }

  override fun validateList(values: List<Timeframe>): ValidationResult {
    val validations = listOf(
      validationListFunc(values.dropLast(1), ::timeframeIsNotOpenEnded, "Only the newest timeframe can be ongoing"),
      validationListFunc(values, ::startBeforeEnd, "Timeframe start must be on or before end"),
      timeframesConsecutive(values, values.firstOrNull()?.start),
      validationListFunc(values, ::startInPresentOrPast, "Timeframe cannot start in the future"),
      validationListFunc(values, ::endInPresentOrPast, "Timeframe cannot end in the future"),
      timeframesNotOverlapping(values)
    )

    return if (validations.all { it.isValid }) ValidationResult.build() else ValidationResult.build(validations.flatMap { it.errors })
  }

  private fun timeframeIsNotOpenEnded(timeframe: Timeframe): Boolean = timeframe.end != null
  private fun startBeforeEnd(timeframe: Timeframe): Boolean =
    timeframe.end == null || timeframe.start.isBefore(timeframe.end)

  private fun startInPresentOrPast(timeframe: Timeframe): Boolean = !timeframe.start.isAfter(LocalDate.now())
  private fun endInPresentOrPast(timeframe: Timeframe): Boolean =
    (timeframe.end?.isBefore(LocalDate.now()) ?: true) || (timeframe.end?.isEqual(LocalDate.now()) ?: true)

  private fun timeframesConsecutive(timeframes: List<Timeframe>, expectedStartDate: LocalDate?): ValidationResult {
    return when {
      timeframes.isEmpty() -> return ValidationResult.build()
      expectedStartDate == null -> return ValidationResult.build("Timeframes must be consecutive")
      timeframes.first().start.isEqual(expectedStartDate) -> timeframesConsecutive(
        timeframes.drop(1),
        timeframes.first().end?.plusDays(1)
      )

      else -> return ValidationResult.build("Timeframes must be consecutive")
    }
  }

  private fun timeframesNotOverlapping(timeframes: List<Timeframe>): ValidationResult =
    if (timeframesNotOverlap(timeframes)) ValidationResult.build()
    else ValidationResult.build("Timeframes must not overlap")

  private fun timeframesNotOverlap(timeframes: List<Timeframe>): Boolean {
    return when {
      timeframes.size < 2 -> true
      else -> timeframes.first().isOverlappingWith(timeframes[1]) && timeframesNotOverlap(timeframes.drop(1))
    }
  }

}