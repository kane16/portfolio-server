package pl.delukesoft.portfolioserver.domain.unit.validator.shortcut

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcutValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase

class ResumeShortcutValidatorTest : ResumeValidatorTestBase() {

  private val validator = ResumeShortcutValidator()

  @Test
  fun `single valid shortcut passes`() {
    val result = validator.validate(
      shortcut(
        title = ofLen(10),
        summary = ofLen(50)
      )
    )

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `title length - exactly 5 is valid`() {
    val result = validator.validate(
      shortcut(
        title = ofLen(5),
        summary = ofLen(50)
      )
    )

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `title length - exactly 30 is valid`() {
    val result = validator.validate(
      shortcut(
        title = ofLen(30),
        summary = ofLen(50)
      )
    )

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @ParameterizedTest
  @ValueSource(ints = [0, 1, 2, 3, 4])
  fun `title must be between 5 and 30 characters - too short`(len: Int) {
    val result = validator.validate(
      shortcut(
        title = ofLen(len),
        summary = ofLen(50)
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Title length must be between 5 and 30")
  }

  @ParameterizedTest
  @ValueSource(ints = [31, 40, 60])
  fun `title must be between 5 and 30 characters - too long`(len: Int) {
    val result = validator.validate(
      shortcut(
        title = ofLen(len),
        summary = ofLen(50)
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Title length must be between 5 and 30")
  }

  @Test
  fun `summary length - exactly 30 is valid`() {
    val result = validator.validate(
      shortcut(
        title = ofLen(10),
        summary = ofLen(30)
      )
    )

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `summary length - exactly 100 is valid`() {
    val result = validator.validate(
      shortcut(
        title = ofLen(10),
        summary = ofLen(100)
      )
    )

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @ParameterizedTest
  @ValueSource(ints = [0, 10, 20, 29])
  fun `summary must be between 30 and 100 characters - too short`(len: Int) {
    val result = validator.validate(
      shortcut(
        title = ofLen(10),
        summary = ofLen(len)
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Summary length must be between 30 and 100")
  }

  @ParameterizedTest
  @ValueSource(ints = [1001, 1200, 2000])
  fun `summary must be between 30 and 100 characters - too long`(len: Int) {
    val result = validator.validate(
      shortcut(
        title = ofLen(10),
        summary = ofLen(len)
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Summary length must be between 30 and 1000")
  }

  @Test
  fun `aggregates multiple errors - title too short and summary too long`() {
    val result = validator.validate(
      shortcut(
        title = ofLen(3),
        summary = ofLen(1500)
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Title length must be between 5 and 30")
    assertHasMessage(result, "Summary length must be between 30 and 1000")
  }


}