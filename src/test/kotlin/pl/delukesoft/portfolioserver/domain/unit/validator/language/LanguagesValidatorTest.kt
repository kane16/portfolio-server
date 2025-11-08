package pl.delukesoft.portfolioserver.domain.unit.validator.language

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.language.LanguagesValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase

class LanguagesValidatorTest : ResumeValidatorTestBase() {

  private val validator = LanguagesValidator(constraintService)

  @Test
  fun `valid resume with 2 distinct languages passes`() {

    val result = validator.validateList(
      listOf(
        language(name = "English", level = anyLevel()),
        language(name = "Polish", level = otherLevel())
      )
    )

    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `zero languages is invalid - requires at least two languages`() {
    val result = validator.validateList(
      emptyList()
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "At least two languages are required")
  }

  @Test
  fun `one language is invalid - requires at least two languages`() {

    val result = validator.validateList(
      listOf(
        language(name = "English", level = anyLevel())
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "At least two languages are required")
  }

  @ParameterizedTest
  @ValueSource(strings = ["", " ", "ab", "  ab  "])
  fun `language name must be at least 3 characters after trim`(raw: String) {
    val result = validator.validateList(
      listOf(
        language(name = raw, level = anyLevel()),
        language(name = "Polish", level = otherLevel())
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Language name must be at least 3 characters")
  }

  @Test
  fun `duplicate languages by name (trim+case-insensitive) are rejected with specific message`() {
    val result = validator.validateList(
      listOf(
        language(name = "English", level = anyLevel()),
        language(name = " english ", level = otherLevel())
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Language cannot be duplicated")
  }

  @Test
  fun `aggregates multiple errors - name too short and duplicate name, no at-least-two error`() {
    val result = validator.validateList(
      listOf(
        language(name = "ab", level = anyLevel()),
        language(name = "Polish", level = anyLevel()),
        language(name = " polish ", level = otherLevel())
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Language name must be at least 3 characters")
    assertHasMessage(result, "Language cannot be duplicated")
    assertFalse(messages(result).any { it.contains("At least two languages are required") })
  }

  @Test
  fun `names normalized by trim+lowercase define identity`() {
    val result = validator.validateList(
      listOf(
        language(name = "  Spanish", level = anyLevel()),
        language(name = "spanish  ", level = otherLevel())
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Language cannot be duplicated")
  }

  @Test
  fun `names must be Capitalized and trimmed`() {
    val result = validator.validateList(
      listOf(
        language(name = "  english ", level = anyLevel()),
        language(name = " english  ", level = otherLevel())
      )
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Language name must be capitalized")
    assertHasMessage(result, "Language cannot be duplicated")
    assertHasMessage(result, "Language name must not contain leading or trailing spaces")
  }


}