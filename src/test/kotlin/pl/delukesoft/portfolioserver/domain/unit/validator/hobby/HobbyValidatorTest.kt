package pl.delukesoft.portfolioserver.domain.unit.validator.hobby

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase
import kotlin.test.Test

class HobbyValidatorTest : ResumeValidatorTestBase() {

  private val validator = HobbyValidator(constraintService)

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
  @ValueSource(strings = ["ab", "ab ", "ab  "])
  fun `hobby name must be at least 3 characters`(raw: String) {
    val result = validator.validateList(listOf(hobby(raw)))
    assertFalse(result.isValid)
    assertHasMessage(result, "resume.hobby.name length must be at least 3")
  }

}