package pl.delukesoft.portfolioserver.domain.unit.validator.business

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase

class BusinessValidatorTest : ResumeValidatorTestBase() {

  private val validator = BusinessValidator(constraintService)

  @Test
  fun `single valid business passes`() {
    val result = validator.validate(
      business(name = "Acme")
    )

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `boundary length - exactly 4 letters is valid`() {
    val result = validator.validate(
      business(name = "Abcd")
    )

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @ParameterizedTest
  @ValueSource(strings = ["A", "Ab"])
  fun `business name must be at least 3 letters`(raw: String) {
    val result = validator.validate(
      business(name = raw)
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "resume.experience.business.name length must be at least 3")
  }

  @Test
  fun `aggregates multiple errors - too short and contains non-letter`() {
    val result = validator.validate(
      business(name = "A1")
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "resume.experience.business.name length must be at least 3")
  }

}