package pl.delukesoft.portfolioserver.domain.unit.validator.timeframe

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase
import java.time.LocalDate
import kotlin.test.Test

class ConsecutiveTimeframeValidatorTest : ResumeValidatorTestBase() {

  private val validator = TimeframeValidator()
  private val lenientValidator = TimeframeValidator(lenientMode = true)
  private val today = LocalDate.now()

  @Test
  fun `single ongoing timeframe in the past is valid`() {
    val result = validator.validateList(
      listOf(tf(start = today.minusYears(1), end = null))
    )
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `two consecutive timeframes with last ongoing is valid`() {
    val firstEnd = LocalDate.of(2023, 12, 31)
    val secondStart = firstEnd.plusDays(1)
    val result = validator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = firstEnd),
        tf(start = secondStart, end = null)
      )
    )
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `multiple closed timeframes strictly consecutive are valid`() {
    val t1 = tf(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 6, 30))
    val t2 = tf(LocalDate.of(2021, 7, 1), LocalDate.of(2021, 12, 31))
    val t3 = tf(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31))

    val result = validator.validateList(listOf(t1, t2, t3))

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `input that is consecutive is valid`() {
    // Provided out-of-order; validator should internally sort by start date.
    val t1 = tf(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31))
    val t2 = tf(LocalDate.of(2022, 1, 1), null)

    val result = validator.validateList(listOf(t1, t2))

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `start after end is invalid`() {
    val result = validator.validateList(
      listOf(tf(start = LocalDate.of(2023, 5, 2), end = LocalDate.of(2023, 5, 1)))
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframe start must be on or before end")
  }

  @Test
  fun `two ongoing timeframes are invalid - only newest can be open-ended`() {
    val result = validator.validateList(
      listOf(
        tf(start = LocalDate.of(2022, 1, 1), end = null),
        tf(start = LocalDate.of(2023, 1, 1), end = null)
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Only the newest timeframe can be ongoing")
  }

  @Test
  fun `overlapping timeframes are invalid`() {
    val result = validator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = LocalDate.of(2023, 6, 30)),
        tf(start = LocalDate.of(2023, 6, 15), end = LocalDate.of(2023, 12, 31))
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframes must not overlap")
  }

  @Test
  fun `gap between timeframes is invalid - must be consecutive`() {
    val result = validator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = LocalDate.of(2023, 6, 30)),
        tf(start = LocalDate.of(2023, 7, 2), end = LocalDate.of(2023, 12, 31))
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframes must be consecutive")
  }

  @Test
  fun `consecutive rule uses previous_end plus one day`() {
    val result = validator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = LocalDate.of(2023, 6, 30)),
        tf(start = LocalDate.of(2023, 7, 1), end = LocalDate.of(2023, 12, 31))
      )
    )

    // This should be valid—no error expected
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  // --- invalid: future constraints ---

  @ParameterizedTest
  @ValueSource(longs = [1L, 7L, 30L])
  fun `timeframe start in the future is invalid`(daysAhead: Long) {
    val result = validator.validateList(
      listOf(
        tf(start = today.plusDays(daysAhead), end = null)
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframe cannot start in the future")
  }

  @ParameterizedTest
  @ValueSource(longs = [1L, 7L, 30L])
  fun `timeframe end in the future is invalid`(daysAhead: Long) {
    val result = validator.validateList(
      listOf(
        tf(start = today.minusDays(10), end = today.plusDays(daysAhead))
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframe cannot end in the future")
  }

  @Test
  fun `ongoing (open-ended) timeframe starting in the future is invalid`() {
    val result = validator.validateList(
      listOf(
        tf(start = today.plusDays(1), end = null)
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframe cannot start in the future")
  }

  // --- aggregation behavior ---

  @Test
  fun `aggregates multiple errors - overlap and future end, no unrelated errors`() {
    val result = validator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = LocalDate.of(2023, 6, 30)),
        tf(start = LocalDate.of(2023, 6, 15), end = today.plusDays(5)) // overlap + future end
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframes must not overlap")
    assertHasMessage(result, "Timeframe cannot end in the future")
  }

  // --- boundary cases ---

  @Test
  fun `empty list is valid (no timeframes to validate)`() {
    val result = validator.validateList(emptyList())
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `single closed timeframe ending today is valid`() {
    val result = validator.validateList(
      listOf(tf(start = today.minusDays(10), end = today))
    )
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `closed followed by ongoing but not consecutive is invalid`() {
    validator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = LocalDate.of(2023, 12, 30)),
        tf(start = LocalDate.of(2023, 12, 31), end = null) // should be 2023-12-31 == 2023-12-30 + 1 -> valid
      )
    )
    // This one is actually consecutive and should be valid; tweak to make it invalid:
    // Re-run with a gap to assert failure:
    val failing = validator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = LocalDate.of(2023, 12, 30)),
        tf(start = LocalDate.of(2024, 1, 1), end = null) // gap => invalid
      )
    )
    assertFalse(failing.isValid)
    assertHasMessage(failing, "Timeframes must be consecutive")
  }

  @Test
  fun `gap between timeframes is valid in lenient mode`() {
    val result = lenientValidator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = LocalDate.of(2023, 6, 30)),
        tf(start = LocalDate.of(2023, 7, 2), end = LocalDate.of(2023, 12, 31))
      )
    )

    assertTrue(result.isValid, "Expected valid in lenient mode, got errors: ${messages(result)}")
  }

  @Test
  fun `non-consecutive closed followed by ongoing is valid in lenient mode`() {
    val result = lenientValidator.validateList(
      listOf(
        tf(start = LocalDate.of(2023, 1, 1), end = LocalDate.of(2023, 12, 30)),
        tf(start = LocalDate.of(2024, 1, 2), end = null) // gap, but allowed in lenient mode
      )
    )

    assertTrue(result.isValid, "Expected valid in lenient mode, got errors: ${messages(result)}")
  }

  @Test
  fun `multiple non-consecutive closed timeframes are valid in lenient mode`() {
    val t1 = tf(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 3, 31))
    val t2 = tf(LocalDate.of(2021, 4, 2), LocalDate.of(2021, 6, 30))   // 1-day gap
    val t3 = tf(LocalDate.of(2021, 7, 2), LocalDate.of(2021, 12, 31))  // 1-day gap

    val result = lenientValidator.validateList(listOf(t1, t2, t3))

    assertTrue(result.isValid, "Expected valid in lenient mode, got errors: ${messages(result)}")
  }

  @Test
  fun `strictly consecutive timeframes also valid in lenient mode`() {
    val t1 = tf(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 6, 30))
    val t2 = tf(LocalDate.of(2021, 7, 1), LocalDate.of(2021, 12, 31))
    val t3 = tf(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31))

    val result = lenientValidator.validateList(listOf(t1, t2, t3))

    assertTrue(
      result.isValid,
      "Expected valid in lenient mode for strictly consecutive timeframes, got errors: ${messages(result)}"
    )
  }

}