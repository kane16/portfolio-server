package pl.delukesoft.portfolioserver.domain.unit.skillexperience

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperienceValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase

class SkillExperienceValidatorTest : ResumeValidatorTestBase() {

  private val validator = SkillExperienceValidator()

  @Test
  fun `single valid skill experience passes`() {
    val result = validator.validateList(listOf(se(level = 4, detail = "Solid production experience")))
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `boundary levels 1 and 5 are valid`() {
    val r1 = validator.validateList(listOf(se(level = 1, detail = ofLen(10))))
    val r5 = validator.validateList(listOf(se(level = 5, detail = ofLen(20))))
    assertTrue(r1.isValid, "Expected valid at level=1: ${messages(r1)}")
    assertTrue(r5.isValid, "Expected valid at level=5: ${messages(r5)}")
  }

  @Test
  fun `multiple valid entries pass`() {
    val result = validator.validateList(
      listOf(
        se(skill("Kotlin"), level = 5, detail = "Leadership, performance tuning"),
        se(skill("Spring"), level = 3, detail = "Built REST APIs at scale")
      )
    )
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @ParameterizedTest
  @ValueSource(ints = [0, -1, 6, 42])
  fun `level outside 1 to 5 is invalid`(lvl: Int) {
    val result = validator.validateList(listOf(se(level = lvl, detail = ofLen(12))))
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience Skill Level must be between 1 and 5")
  }

  @ParameterizedTest
  @ValueSource(ints = [0, 1, 5, 9])
  fun `detail shorter than 10 characters is invalid`(len: Int) {
    val result = validator.validateList(listOf(se(level = 3, detail = ofLen(len))))
    assertFalse(result.isValid)
    assertHasMessage(result, "Detail must be at least 10 characters")
  }

  @Test
  fun `detail exactly 10 characters is valid`() {
    val result = validator.validateList(listOf(se(level = 2, detail = ofLen(10))))
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  // --- aggregation behavior ---

  @Test
  fun `aggregates multiple errors - bad level and too-short detail`() {
    val result = validator.validateList(listOf(se(level = 0, detail = ofLen(3))))
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience Skill Level must be between 1 and 5")
    assertHasMessage(result, "Detail must be at least 10 characters")
  }

  // --- multiple entries, some invalid ---

  @Test
  fun `mix of valid and invalid entries returns all relevant errors`() {
    val list = listOf(
      se(skill("Kotlin"), level = 5, detail = "Mentored team members"), // valid
      se(skill("React"), level = 6, detail = "Frontend work"),          // bad level
      se(skill("MongoDB"), level = 3, detail = ofLen(8))                // short detail
    )

    val result = validator.validateList(list)

    assertFalse(result.isValid)
    assertHasMessage(result, "Experience Skill Level must be between 1 and 5")
    assertHasMessage(result, "Detail must be at least 10 characters")
  }

}