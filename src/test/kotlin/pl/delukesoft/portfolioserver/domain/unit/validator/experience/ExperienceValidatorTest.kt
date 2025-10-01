package pl.delukesoft.portfolioserver.domain.unit.validator.experience

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.experience.ExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainValidator
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase
import java.time.LocalDate
import kotlin.test.Test

class ExperienceValidatorTest : ResumeValidatorTestBase() {

  private val validator = ExperienceValidator(
    TimeframeValidator(),
    BusinessValidator(),
    SkillExperienceValidator(SkillValidator(SkillDomainValidator()))
  )
  private val today = LocalDate.now()

  @Test
  fun `single valid experience passes`() {
    val result = validator.validate(exp())
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `position boundary lengths 6 and 30 are valid`() {
    val r6 = validator.validate(exp(position = ofLen(6)))
    val r30 = validator.validate(exp(position = ofLen(30)))
    assertTrue(r6.isValid, "Expected valid at 6 chars: ${messages(r6)}")
    assertTrue(r30.isValid, "Expected valid at 30 chars: ${messages(r30)}")
  }

  @Test
  fun `summary boundary lengths 10 and 100 are valid`() {
    val r10 = validator.validate(exp(summary = ofLen(10)))
    val r100 = validator.validate(exp(summary = ofLen(100)))
    assertTrue(r10.isValid, "Expected valid at 10 chars: ${messages(r10)}")
    assertTrue(r100.isValid, "Expected valid at 100 chars: ${messages(r100)}")
  }

  @Test
  fun `list of strictly consecutive experiences passes (strict mode)`() {
    val e1 = exp(
      timeframeStart = LocalDate.of(2023, 1, 1),
      timeframeEnd = LocalDate.of(2023, 6, 30)
    )
    val e2 = exp(
      timeframeStart = LocalDate.of(2023, 7, 1), // previous end + 1 day
      timeframeEnd = null // ongoing
    )
    val result = validator.validateList(listOf(e1, e2))
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  // --- position length rules ---

  @ParameterizedTest
  @ValueSource(ints = [0, 1, 2, 3, 4, 5])
  fun `position shorter than 6 characters is invalid`(len: Int) {
    val result = validator.validate(exp(position = ofLen(len)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience position must be between 6 and 30 characters")
  }

  @ParameterizedTest
  @ValueSource(ints = [31, 100])
  fun `position longer than 30 characters is invalid`(len: Int) {
    val result = validator.validate(exp(position = ofLen(len)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience position must be between 6 and 30 characters")
  }

  // --- summary length rules ---

  @ParameterizedTest
  @ValueSource(ints = [0, 1, 5, 9])
  fun `summary shorter than 10 characters is invalid`(len: Int) {
    val result = validator.validate(exp(summary = ofLen(len)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience summary must be between 10 and 100 characters")
  }

  @ParameterizedTest
  @ValueSource(ints = [101, 200])
  fun `summary longer than 100 characters is invalid`(len: Int) {
    val result = validator.validate(exp(summary = ofLen(len)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience summary must be between 10 and 100 characters")
  }

  @Test
  fun `invalid business bubbles up its errors`() {
    val badBusinessName = "aCme1"
    val result = validator.validate(
      exp().copy(business = business(name = badBusinessName))
    )

    assertFalse(result.isValid)
    assertHasMessage(result, "Business name must be capitalized")
    assertHasMessage(result, "Business name must contain only letters (no spaces)")
  }

  @Test
  fun `invalid skill experience bubbles up its errors`() {
    val invalidSkills = listOf(
      se(level = 6, detail = ofLen(8))
    )
    val result = validator.validate(exp(skills = invalidSkills))

    assertFalse(result.isValid)
    assertHasMessage(result, "Experience Skill Level must be between 1 and 5")
    assertHasMessage(result, "Detail must be at least 10 characters")
  }

  @Test
  fun `mix of valid and invalid skill experiences returns all relevant errors`() {
    val skills = listOf(
      se(level = 5, detail = "Architected system modules"), // valid
      se(level = 0, detail = ofLen(12)),                    // bad level
      se(level = 3, detail = ofLen(5))                      // short detail
    )
    val result = validator.validate(exp(skills = skills))

    assertFalse(result.isValid)
    assertHasMessage(result, "Experience Skill Level must be between 1 and 5")
    assertHasMessage(result, "Detail must be at least 10 characters")
  }

  // --- timeframe list validation (strict consecutive mode) ---

  @Test
  fun `overlapping experiences are invalid`() {
    val e1 = exp(
      timeframeStart = LocalDate.of(2023, 1, 1),
      timeframeEnd = LocalDate.of(2023, 6, 30)
    )
    val e2 = exp(
      timeframeStart = LocalDate.of(2023, 6, 15), // overlaps
      timeframeEnd = LocalDate.of(2023, 12, 31)
    )

    val result = validator.validateList(listOf(e1, e2))

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframes must not overlap")
  }

  @Test
  fun `gap between experiences is invalid in strict mode`() {
    val e1 = exp(
      timeframeStart = LocalDate.of(2023, 1, 1),
      timeframeEnd = LocalDate.of(2023, 6, 30)
    )
    val e2 = exp(
      timeframeStart = LocalDate.of(2023, 7, 2), // 1-day gap => invalid in strict mode
      timeframeEnd = LocalDate.of(2023, 12, 31)
    )

    val result = validator.validateList(listOf(e1, e2))

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframes must be consecutive")
  }

  @Test
  fun `only newest experience can be ongoing`() {
    val e1 = exp(
      timeframeStart = LocalDate.of(2022, 1, 1),
      timeframeEnd = null
    )
    val e2 = exp(
      timeframeStart = LocalDate.of(2023, 1, 1),
      timeframeEnd = null
    )

    val result = validator.validateList(listOf(e1, e2))

    assertFalse(result.isValid)
    assertHasMessage(result, "Only the newest timeframe can be ongoing")
  }

  @Test
  fun `timeframe start after end is invalid`() {
    val e = exp(
      timeframeStart = LocalDate.of(2023, 5, 2),
      timeframeEnd = LocalDate.of(2023, 5, 1)
    )

    val result = validator.validateList(listOf(e))

    assertFalse(result.isValid)
    assertHasMessage(result, "Timeframe start must be on or before end")
  }

  @Test
  fun `future start or end is invalid`() {
    val futureStart = exp(
      timeframeStart = today.plusDays(3),
      timeframeEnd = null
    )
    val futureEnd = exp(
      timeframeStart = today.minusDays(10),
      timeframeEnd = today.plusDays(2)
    )

    val r1 = validator.validateList(listOf(futureStart))
    val r2 = validator.validateList(listOf(futureEnd))

    assertFalse(r1.isValid)
    assertHasMessage(r1, "Timeframe cannot start in the future")

    assertFalse(r2.isValid)
    assertHasMessage(r2, "Timeframe cannot end in the future")
  }

  @Test
  fun `aggregates multiple errors across business, position, summary and skills`() {
    val badBusiness = business(name = "acme1") // not capitalized + non-letter
    val result = validator.validate(
      exp(
        position = ofLen(5),        // too short
        summary = ofLen(9),        // too short
        skills = listOf(se(level = 0, detail = ofLen(5))) // invalid level + short detail
      ).copy(business = badBusiness)
    )

    assertFalse(result.isValid)
    // position and summary
    assertHasMessage(result, "Experience position must be between 6 and 30 characters")
    assertHasMessage(result, "Experience summary must be between 10 and 100 characters")
    assertHasMessage(result, "Business name must be capitalized")
    assertHasMessage(result, "Business name must contain only letters (no spaces)")
    assertHasMessage(result, "Skill Level must be between 1 and 5")
    assertHasMessage(result, "Detail must be at least 10 characters")
  }

  @Test
  fun `description exactly 10 characters is valid`() {
    val result = validator.validate(exp().copy(description = ofLen(10)))
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `description exactly 300 characters is valid`() {
    val result = validator.validate(exp().copy(description = ofLen(300)))
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @ParameterizedTest
  @ValueSource(ints = [0, 1, 5, 9])
  fun `description shorter than 10 characters is invalid`(len: Int) {
    val result = validator.validate(exp().copy(description = ofLen(len)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience description must be between 10 and 300 characters")
  }

  @ParameterizedTest
  @ValueSource(ints = [301, 400, 500])
  fun `description longer than 300 characters is invalid`(len: Int) {
    val result = validator.validate(exp().copy(description = ofLen(len)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience description must be between 10 and 300 characters")
  }


}