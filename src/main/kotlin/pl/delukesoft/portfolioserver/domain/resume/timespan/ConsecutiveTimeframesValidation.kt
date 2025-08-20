package pl.delukesoft.portfolioserver.domain.resume.timespan

import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator
import java.time.LocalDate

class ConsecutiveTimeframesValidation : Validator<List<Timeframe>>() {

  override fun validate(value: List<Timeframe>): ValidationResult {
    val validations = listOf(
      timeframesValidation(value.dropLast(1), ::timeframeIsNotOpenEnded, "Only the newest timeframe can be ongoing"),
      timeframesValidation(value, ::startBeforeEnd, "Timeframe start must be on or before end"),
      timeframesConsecutive(value, value.firstOrNull()?.start),
      timeframesValidation(value, ::startInPresentOrPast, "Timeframe cannot start in the future"),
      timeframesValidation(value, ::endInPresentOrPast, "Timeframe cannot end in the future"),
      timeframesNotOverlapping(value)
    )

    return if (validations.all { it.isValid }) ValidationResult.build() else ValidationResult.build(validations.flatMap { it.errors })
  }

  private fun timeframesValidation(
    timeframes: List<Timeframe>,
    validationFunc: (timeframe: Timeframe) -> Boolean,
    message: String
  ): ValidationResult =
    if (timeframes.all { validationFunc(it) }) ValidationResult.build() else ValidationResult.build(message)

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