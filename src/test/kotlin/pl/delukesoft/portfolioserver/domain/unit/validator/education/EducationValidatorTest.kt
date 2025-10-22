package pl.delukesoft.portfolioserver.domain.unit.validator.education

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.education.Education
import pl.delukesoft.portfolioserver.domain.resume.education.EducationInstitution
import pl.delukesoft.portfolioserver.domain.resume.education.EducationType
import pl.delukesoft.portfolioserver.domain.resume.education.EducationValidator
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase
import java.time.LocalDate

class EducationValidatorTest : ResumeValidatorTestBase() {

  private val validator = EducationValidator(
    TimeframeValidator(false)
  )

  private val today: LocalDate = LocalDate.now()

  // Helpers
  private fun institution(
    name: String = "Uni",
    city: String = "City",
    country: String = "Country"
  ) = EducationInstitution(name = name, city = city, country = country)

  private fun edu(
    title: String = ofLen(10),
    institution: EducationInstitution = institution(),
    fieldOfStudy: String = "Computer Science",
    grade: Double = 4.5,
    type: EducationType? = EducationType.DEGREE,
    start: LocalDate = LocalDate.of(2020, 1, 1),
    end: LocalDate? = LocalDate.of(2023, 6, 30),
    description: String? = null,
    externalLinks: List<String> = emptyList()
  ): Education = Education(
    title = title,
    institution = institution,
    timeframe = Timeframe(start = start, end = end),
    fieldOfStudy = fieldOfStudy,
    grade = grade,
    type = type,
    description = description,
    externalLinks = externalLinks
  )

  @Test
  fun `valid education passes`() {
    val result = validator.validate(edu())
    assertTrue(result.isValid, "Expected valid, got errors: ${messages(result)}")
  }

  @Test
  fun `title boundary lengths 5 and 30 are valid`() {
    val r5 = validator.validate(edu(title = ofLen(5)))
    val r30 = validator.validate(edu(title = ofLen(30)))
    assertTrue(r5.isValid, "Expected valid at 5 chars: ${messages(r5)}")
    assertTrue(r30.isValid, "Expected valid at 30 chars: ${messages(r30)}")
  }

  @ParameterizedTest
  @ValueSource(ints = [0, 1, 2, 3, 4])
  fun `title shorter than 5 characters is invalid`(len: Int) {
    val result = validator.validate(edu(title = ofLen(len)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Education title must be between 5 and 30 characters")
  }

  @ParameterizedTest
  @ValueSource(ints = [31, 50])
  fun `title longer than 30 characters is invalid`(len: Int) {
    val result = validator.validate(edu(title = ofLen(len)))
    assertFalse(result.isValid)
    assertHasMessage(result, "Education title must be between 5 and 30 characters")
  }

  @Test
  fun `institution name cannot be empty`() {
    val result = validator.validate(edu(institution = institution(name = "")))
    assertFalse(result.isValid)
    assertHasMessage(result, "Institution name must not be empty")
  }

  @Test
  fun `institution city cannot be empty`() {
    val result = validator.validate(edu(institution = institution(city = "")))
    assertFalse(result.isValid)
    assertHasMessage(result, "Institution city must not be empty")
  }

  @Test
  fun `institution country cannot be empty`() {
    val result = validator.validate(edu(institution = institution(country = "")))
    assertFalse(result.isValid)
    assertHasMessage(result, "Institution country must not be empty")
  }

  @Test
  fun `field of study cannot be empty`() {
    val result = validator.validate(edu(fieldOfStudy = ""))
    assertFalse(result.isValid)
    assertHasMessage(result, "Field of study must not be empty")
  }

  @Test
  fun `education type must not be null`() {
    val result = validator.validate(edu(type = null))
    assertFalse(result.isValid)
    assertHasMessage(result, "Education type must not be null")
  }

  @Test
  fun `timeframe start must be strictly before end`() {
    val start = today.minusYears(1)
    val end = today.minusYears(1)
    val eq = validator.validate(edu(start = start, end = end))
    val inv = validator.validate(edu(start = today, end = today.minusDays(1)))

    assertFalse(eq.isValid)
    assertHasMessage(eq, "Timeframe start must be on or before end")

    assertFalse(inv.isValid)
    assertHasMessage(inv, "Timeframe start must be on or before end")
  }

  @Test
  fun `Empty education should show an error`() {
    val result = validator.validateList(emptyList())
    assertFalse(result.isValid)
    assertHasMessage(
      result,
      "Education list must not be empty"
    )
  }
}
