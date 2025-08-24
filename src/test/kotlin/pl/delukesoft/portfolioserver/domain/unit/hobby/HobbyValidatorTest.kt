package pl.delukesoft.portfolioserver.domain.unit.hobby

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase
import kotlin.test.Test

class HobbyValidatorTest : ResumeValidatorTestBase() {

  private val validator = HobbyValidator()

  @Test
  fun `valid hobby passes`() {
    val result = validator.validateList(listOf(hobby("Fishing")))
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @ParameterizedTest
  @ValueSource(strings = ["", "   "])
  fun `hobby name must not be empty`(raw: String) {
    val result = validator.validateList(listOf(hobby(raw)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Hobby name must not be empty")
  }

  @ParameterizedTest
  @ValueSource(strings = ["fishing", "FISHING", "fISHING"])
  fun `hobby name must be capitalized`(raw: String) {
    val result = validator.validateList(listOf(hobby(raw)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Hobby name must be capitalized")
  }

  @ParameterizedTest
  @ValueSource(strings = [" Fishing", "Fishing "])
  fun `hobby name must not contain trailing or heading spaces`(raw: String) {
    val result = validator.validateList(listOf(hobby(raw)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Hobby name must not contain spaces")
  }

  @Test
  fun `multiple errors aggregated`() {
    val result = validator.validateList(listOf(hobby(" fishing ")))
    assertFalse(result.isValid)
    assertHasMessage(result, "Hobby name must be capitalized")
    assertHasMessage(result, "Hobby name must not contain spaces")
  }

}