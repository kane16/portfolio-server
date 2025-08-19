package pl.delukesoft.portfolioserver.domain.unit.skill

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillsValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase

class SkillsValidatorTest : ResumeValidatorTestBase() {

  private val validator: SkillsValidator = SkillsValidator()

  @Test
  fun `valid resume with 2 distinct skills and unique domains passes`() {
    val r = resumeWithSkills(
      skill(
        name = "Kotlin",
        level = 4,
        domains = listOf(domain("Backend"), domain("JVM"))
      ),
      skill(
        name = "React",
        level = 3,
        domains = listOf(domain("Frontend"))
      )
    )

    val result = validator.validate(r)

    Assertions.assertTrue(result.isValid, "Expected valid result, got errors: ${messages(result)}")
  }

  @Test
  fun `zero skills is invalid - requires at least two skills`() {
    val r = resumeWithSkills()

    val result = validator.validate(r)

    Assertions.assertFalse(result.isValid)
    assertHasMessage(result, "At least one skill is required")
  }

  @Test
  fun `one skill is valid - requires at least one skill`() {
    val r = resumeWithSkills(skill(name = "Kotlin", level = 3))

    val result = validator.validate(r)

    Assertions.assertTrue(result.isValid)
  }

  @ParameterizedTest
  @ValueSource(strings = ["", " "])
  fun `skill name must be at least 1 character after trim`(raw: String) {
    val r = resumeWithSkills(
      skill(name = raw, level = 3),
      skill(name = "React", level = 3)
    )

    val result = validator.validate(r)

    Assertions.assertFalse(result.isValid)
    assertHasMessage(result, "Skill name must be at least 1 character")
  }

  @Test
  fun `level below 1 is invalid with specific message`() {
    val r = resumeWithSkills(
      skill(name = "Kotlin", level = 0),
      skill(name = "React", level = 3)
    )

    val result = validator.validate(r)

    Assertions.assertFalse(result.isValid)
    assertHasMessage(result, "Skill level must be between 1 and 5")
  }

  @Test
  fun `level above 5 is invalid with specific message`() {
    val r = resumeWithSkills(
      skill(name = "Kotlin", level = 6),
      skill(name = "React", level = 3)
    )

    val result = validator.validate(r)

    Assertions.assertFalse(result.isValid)
    assertHasMessage(result, "Skill level must be between 1 and 5")
  }

  @Test
  fun `duplicate skills by name (trim+case-insensitive) are rejected with specific message`() {
    val r = resumeWithSkills(
      skill(name = "Kotlin", level = 3),
      skill(name = " kotlin ", level = 4)
    )

    val result = validator.validate(r)

    Assertions.assertFalse(result.isValid)
    assertHasMessage(result, "Skill cannot be duplicated")
  }

  @Test
  fun `duplicate domains within a single skill (trim+case-insensitive) are rejected`() {
    val r = resumeWithSkills(
      skill(
        name = "Kotlin",
        level = 3,
        domains = listOf(domain("Backend"), domain(" backend "), domain("BACKEND"))
      ),
      skill(name = "React", level = 3)
    )

    val result = validator.validate(r)

    Assertions.assertFalse(result.isValid)
    assertHasMessage(result, "Skill domain cannot be duplicated")
  }

  @Test
  fun `aggregates multiple errors - name too short, bad level, duplicate domain, too few skills is NOT triggered`() {
    val r = resumeWithSkills(
      skill(
        name = "",
        level = 0,
        domains = listOf(domain("Backend"), domain(" backend ")) // duplicate domains
      ),
      skill(name = "React", level = 6)
    )

    val result = validator.validate(r)

    Assertions.assertFalse(result.isValid)
    assertHasMessage(result, "Skill name must be at least 1 character")
    assertHasMessage(result, "Skill level must be between 1 and 5")
    assertHasMessage(result, "Skill domain cannot be duplicated")
    Assertions.assertFalse(messages(result).any { it.contains("At least two skills are required") })
  }

  @Test
  fun `two distinct skills with similar names but different after normalization should pass`() {
    val r = resumeWithSkills(
      skill(name = "C++", level = 3),
      skill(name = "C#", level = 3)
    )

    val result = validator.validate(r)

    Assertions.assertTrue(result.isValid, "Expected valid; got: ${messages(result)}")
  }

}