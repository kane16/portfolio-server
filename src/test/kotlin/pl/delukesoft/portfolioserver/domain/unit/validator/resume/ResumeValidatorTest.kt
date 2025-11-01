package pl.delukesoft.portfolioserver.domain.unit.validator.resume

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.delukesoft.portfolioserver.domain.resume.ResumeValidator
import pl.delukesoft.portfolioserver.domain.resume.education.EducationValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.ExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyValidator
import pl.delukesoft.portfolioserver.domain.resume.language.LanguagesValidator
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcutValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainValidator
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator
import pl.delukesoft.portfolioserver.domain.unit.ResumeValidatorTestBase
import pl.delukesoft.portfolioserver.domain.validation.ResumeValidatorResult
import java.time.LocalDate

class ResumeValidatorTest : ResumeValidatorTestBase() {

  private val validator = ResumeValidator(
    ResumeShortcutValidator(),
    SkillValidator(SkillDomainValidator()),
    ExperienceValidator(
      TimeframeValidator(false), BusinessValidator(), SkillExperienceValidator(
        SkillValidator(SkillDomainValidator())
      )
    ),
    ExperienceValidator(
      TimeframeValidator(true), BusinessValidator(), SkillExperienceValidator(
        SkillValidator(SkillDomainValidator())
      )
    ),
    EducationValidator(TimeframeValidator(false)),
    HobbyValidator(),
    LanguagesValidator()
  )

  @Test
  fun `all domains valid, ResumeValidatorResult valid and domainResults are valid`() {
    val resume = baseResume(
      skills = listOf(skill(name = "Kotlin", level = 3)),
      experience = listOf(
        exp(
          timeframeStart = LocalDate.of(2022, 1, 1),
          timeframeEnd = LocalDate.of(2022, 12, 31)
        ),
        exp(
          timeframeStart = LocalDate.of(2023, 1, 1),
          timeframeEnd = null
        )
      ),
      sideProjects = listOf(
        exp(
          timeframeStart = LocalDate.of(2020, 1, 1),
          timeframeEnd = LocalDate.of(2020, 6, 30)
        ),
        exp(
          timeframeStart = LocalDate.of(2020, 7, 2), // lenient allows gap
          timeframeEnd = LocalDate.of(2020, 12, 31)
        )
      )
    ).copy(shortcut = shortcut(title = ofLen(10), summary = ofLen(50)), hobbies = listOf(hobby("Reading")))

    val result = validator.validate(resume) as ResumeValidatorResult

    assertTrue(result.isValid, "Expected overall valid. Errors: ${messages(result)}")
    assertEquals(7, result.domainResults.size)
    assertTrue(result.domainResults.all { it.isValid }, "Expected all domain results valid")
  }

  @Test
  fun `aggregates errors across all domains`() {
    val resume = baseResume(
      skills = emptyList(), // invalid - at least one required
      experience = listOf(
        exp(
          position = ofLen(4), // invalid
          summary = ofLen(5),  // invalid
          timeframeStart = LocalDate.of(2023, 1, 1),
          timeframeEnd = LocalDate.of(2023, 6, 30),
          skills = listOf(se(level = 0, detail = ofLen(5))) // invalid level + detail too short
        ),
        exp(
          timeframeStart = LocalDate.of(2023, 7, 2), // gap - strict invalid
          timeframeEnd = LocalDate.of(2023, 12, 31)
        )
      ),
      sideProjects = listOf(
        // overlapping even in lenient mode should fail
        exp(
          timeframeStart = LocalDate.of(2022, 1, 1),
          timeframeEnd = LocalDate.of(2022, 6, 30)
        ),
        exp(
          timeframeStart = LocalDate.of(2022, 6, 15),
          timeframeEnd = LocalDate.of(2022, 12, 31)
        )
      )
    ).copy(
      shortcut = shortcut(title = ofLen(3), summary = ofLen(10)),
      hobbies = listOf(hobby(" leading")),
      languages = listOf(language("en"))
    )

    val result = validator.validate(resume) as ResumeValidatorResult

    assertFalse(result.isValid)
    // Shortcut
    assertHasMessage(result, "Title length must be between 5 and 30")
    assertHasMessage(result, "Summary length must be between 30 and 100")
    // Skills
    assertHasMessage(result, "At least one skill is required")
    // Experience (strict)
    assertHasMessage(result, "Experience position must be between 6 and 30 characters")
    assertHasMessage(result, "Experience summary must be between 10 and 100 characters")
    assertHasMessage(result, "Timeframes must be consecutive")
    // SkillExperience inside Experience
    assertHasMessage(result, "Experience Skill Level must be between 1 and 5")
    assertHasMessage(result, "Detail must be at least 10 characters")
    // Side projects (lenient but overlapping)
    assertHasMessage(result, "Timeframes must not overlap")
    // Hobby
    assertHasMessage(result, "Hobby name must not contain spaces")
    // Languages
    assertHasMessage(result, "At least two languages are required")
    assertHasMessage(result, "Language name must be at least 3 characters long")
    assertHasMessage(result, "Language name must be capitalized")
  }

  @Test
  fun `strict vs lenient consecutive - strict invalid gap, lenient valid gap`() {
    val strictGapResume = baseResume(
      experience = listOf(
        exp(timeframeStart = LocalDate.of(2023, 1, 1), timeframeEnd = LocalDate.of(2023, 6, 30)),
        exp(
          timeframeStart = LocalDate.of(2023, 7, 2),
          timeframeEnd = LocalDate.of(2023, 12, 31)
        ) // gap -> strict invalid
      ),
      sideProjects = emptyList()
    ).copy(hobbies = listOf(hobby("Running")))

    val lenientGapResume = strictGapResume.copy(
      experience = listOf(
        exp(timeframeStart = LocalDate.of(2023, 1, 1), timeframeEnd = LocalDate.of(2023, 6, 30)),
        exp(timeframeStart = LocalDate.of(2023, 7, 1), timeframeEnd = LocalDate.of(2023, 12, 31))
      ),
      sideProjects = listOf(
        exp(timeframeStart = LocalDate.of(2020, 1, 1), timeframeEnd = LocalDate.of(2020, 6, 30)),
        exp(timeframeStart = LocalDate.of(2020, 7, 2), timeframeEnd = LocalDate.of(2020, 12, 31)) // gap -> lenient OK
      )
    )

    val rStrict = validator.validate(strictGapResume) as ResumeValidatorResult
    val rLenient = validator.validate(lenientGapResume) as ResumeValidatorResult

    assertFalse(rStrict.domainResults.first { it.domainName == "experience" }.isValid)
    assertHasMessage(rStrict, "Timeframes must be consecutive")

    assertTrue(rLenient.domainResults.first { it.domainName == "sideProject" }.isValid)
    assertTrue(rLenient.isValid, "All domains should pass in lenient side-projects case")
  }

  // -------- Shortcut bounds --------

  @ParameterizedTest
  @ValueSource(ints = [5, 30])
  fun `shortcut title bounds are valid`(len: Int) {
    val resume = baseResume(
      experience = emptyList(),
      sideProjects = emptyList()
    ).copy(shortcut = shortcut(title = ofLen(len), summary = ofLen(50)), hobbies = listOf(hobby("Chess")))

    val result = validator.validate(resume)
    assertTrue(result.isValid, "Expected valid, got: ${messages(result)}")
  }

  @ParameterizedTest
  @ValueSource(ints = [0, 1, 2, 3, 4, 31, 40])
  fun `shortcut title outside bounds is invalid`(len: Int) {
    val resume = baseResume(
      experience = emptyList(),
      sideProjects = emptyList()
    ).copy(shortcut = shortcut(title = ofLen(len), summary = ofLen(50)), hobbies = listOf(hobby("Chess")))

    val result = validator.validate(resume)
    assertFalse(result.isValid)
    assertHasMessage(result, "Title length must be between 5 and 30")
  }

  @ParameterizedTest
  @ValueSource(ints = [30, 100])
  fun `shortcut summary bounds are valid`(len: Int) {
    val resume = baseResume(
      experience = emptyList(),
      sideProjects = emptyList()
    ).copy(shortcut = shortcut(title = ofLen(10), summary = ofLen(len)), hobbies = listOf(hobby("Chess")))
    val result = validator.validate(resume)
    assertTrue(result.isValid, "Expected valid, got: ${messages(result)}")
  }

  @ParameterizedTest
  @ValueSource(ints = [0, 10, 29, 1001, 1200])
  fun `shortcut summary outside bounds is invalid`(len: Int) {
    val resume = baseResume(
      experience = emptyList(),
      sideProjects = emptyList()
    ).copy(shortcut = shortcut(title = ofLen(10), summary = ofLen(len)), hobbies = listOf(hobby("Chess")))
    val result = validator.validate(resume)
    assertFalse(result.isValid)
    assertHasMessage(result, "Summary length must be between 30 and 100")
  }

  // -------- Skill rules (list + item) --------

  @Test
  fun `skills - must have at least one, no duplicates, valid bounds`() {
    // empty -> invalid
    val rEmpty = validator.validate(
      baseResume(
        skills = emptyList(),
        experience = emptyList(),
        sideProjects = emptyList()
      )
    )
    assertFalse(rEmpty.isValid)
    assertHasMessage(rEmpty, "At least one skill is required")

    // duplicate by name (case-insensitive)
    val rDup = validator.validate(
      baseResume(
        skills = listOf(skill(name = "Kotlin"), skill(name = "kotlin")),
        experience = emptyList(),
        sideProjects = emptyList()
      )
    )
    assertFalse(rDup.isValid)
    assertHasMessage(rDup, "Skill cannot be duplicated")

    // domain duplication within a skill
    val rDomainDup = validator.validate(
      baseResume(
        skills = listOf(
          skill(name = "Kotlin", domains = listOf(domain("Backend"), domain("backend")))
        ),
        experience = emptyList(),
        sideProjects = emptyList()
      )
    )
    assertFalse(rDomainDup.isValid)
    assertHasMessage(rDomainDup, "Skill domain cannot be duplicated")

    // level bounds and non-empty name
    val rBounds = validator.validate(
      baseResume(
        skills = listOf(
          skill(name = "", level = 0) // name empty + level out of bounds
        ),
        experience = emptyList(),
        sideProjects = emptyList()
      )
    )
    assertFalse(rBounds.isValid)
    assertHasMessage(rBounds, "Skill level must be between 1 and 5")
    assertHasMessage(rBounds, "Skill name must be at least 1 character")

    // valid skill case
    val rValid = validator.validate(
      baseResume(
        skills = listOf(skill(name = "Kotlin", level = 5)),
        experience = emptyList(),
        sideProjects = emptyList()
      )
    )
    assertTrue(rValid.isValid, "Expected valid, got: ${messages(rValid)}")
  }

  // -------- Languages rules --------

  @Test
  fun `languages - at least two, names trimmed, capitalized, no duplicates`() {
    // one language -> invalid
    val rMin = validator.validate(
      baseResume(
        skills = listOf(skill()),
        experience = emptyList(),
        sideProjects = emptyList()
      ).copy(languages = listOf(language("english")))
    )
    assertFalse(rMin.isValid)
    assertHasMessage(rMin, "At least two languages are required")
    assertHasMessage(rMin, "Language name must be capitalized")

    // invalid length and trimmed
    val rTrimLen = validator.validate(
      baseResume(
        skills = listOf(skill()),
        experience = emptyList(),
        sideProjects = emptyList()
      ).copy(languages = listOf(language(" Eng"), language("de")))
    )
    assertFalse(rTrimLen.isValid)
    assertHasMessage(rTrimLen, "Language name must not contain leading or trailing spaces")
    assertHasMessage(rTrimLen, "Language name must be at least 3 characters long")

    // duplicated languages
    val rDup = validator.validate(
      baseResume(
        skills = listOf(skill()),
        experience = emptyList(),
        sideProjects = emptyList()
      ).copy(languages = listOf(language("Polish"), language("polish")))
    )
    assertFalse(rDup.isValid)
    assertHasMessage(rDup, "Language cannot be duplicated")

    // valid
    val rValid = validator.validate(
      baseResume(
        skills = listOf(skill()),
        experience = emptyList(),
        sideProjects = emptyList()
      ).copy(languages = listOf(language("English"), language("German")))
    )
    assertTrue(rValid.isValid, "Expected valid, got: ${messages(rValid)}")
  }

  // -------- Hobby rules --------

  @Test
  fun `hobby - trimmed, capitalized, not empty`() {
    val rTrim = validator.validate(
      baseResume(
        skills = listOf(skill()),
        experience = emptyList(),
        sideProjects = emptyList()
      ).copy(hobbies = listOf(hobby(" space"), hobby("Valid")))
    )
    assertFalse(rTrim.isValid)
    assertHasMessage(rTrim, "Hobby name must not contain spaces")

    val rCap = validator.validate(
      baseResume(
        skills = listOf(skill()),
        experience = emptyList(),
        sideProjects = emptyList()
      ).copy(hobbies = listOf(hobby("valid")))
    )
    assertFalse(rCap.isValid)
    assertHasMessage(rCap, "Hobby name must be capitalized")

    val rEmpty = validator.validate(
      baseResume(
        skills = listOf(skill()),
        experience = emptyList(),
        sideProjects = emptyList()
      ).copy(hobbies = listOf(hobby("   ")))
    )
    assertFalse(rEmpty.isValid)
    assertHasMessage(rEmpty, "Hobby name must not be empty")

    val rValid = validator.validate(
      baseResume(
        skills = listOf(skill()),
        experience = emptyList(),
        sideProjects = emptyList()
      ).copy(hobbies = listOf(hobby("Running")))
    )
    assertTrue(rValid.isValid, "Expected valid, got: ${messages(rValid)}")
  }

  // -------- Experience item bounds + timeframe strict checks --------

  @Test
  fun `experience - position and summary bounds valid at edges`() {
    val resume = baseResume(
      experience = listOf(
        exp(
          position = ofLen(6),
          summary = ofLen(10),
          timeframeStart = LocalDate.of(2022, 1, 1),
          timeframeEnd = LocalDate.of(2022, 12, 31)
        ),
        exp(position = ofLen(30), summary = ofLen(100), timeframeStart = LocalDate.of(2023, 1, 1), timeframeEnd = null)
      ),
      sideProjects = emptyList()
    ).copy(hobbies = listOf(hobby("Reading")))
    val result = validator.validate(resume)
    assertTrue(result.isValid, "Expected valid, got: ${messages(result)}")
  }

  @Test
  fun `experience - invalid position, summary and description collected`() {
    val resume = baseResume(
      experience = listOf(
        exp(
          position = ofLen(5), // invalid
          summary = ofLen(9),  // invalid
          timeframeStart = LocalDate.of(2022, 1, 1),
          timeframeEnd = LocalDate.of(2022, 12, 31)
        ).copy(description = ofLen(5)) // invalid description
      ),
      sideProjects = emptyList()
    ).copy(hobbies = listOf(hobby("Reading")))

    val result = validator.validate(resume)
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience position must be between 6 and 30 characters")
    assertHasMessage(result, "Experience summary must be between 10 and 100 characters")
    assertHasMessage(result, "Experience description must be between 10 and 300 characters")
  }

  @Test
  fun `experience - business validation errors are included`() {
    val resume = baseResume(
      experience = listOf(
        exp().copy(business = business("ac ")) // not capitalized + non-letters + less than 3 letters
      ),
      sideProjects = emptyList()
    ).copy(hobbies = listOf(hobby("Reading")))

    val result = validator.validate(resume)
    assertFalse(result.isValid)
    assertHasMessage(result, "Business name must be capitalized")
    assertHasMessage(result, "Business name must be at least 3 letters")
    assertHasMessage(result, "Business name must contain only letters (no spaces)")
  }

  @Test
  fun `experience - skill experience level bounds and detail length`() {
    val resume = baseResume(
      experience = listOf(
        exp(skills = listOf(se(level = 0, detail = ofLen(9)), se(level = 6, detail = ofLen(9))))
      ),
      sideProjects = emptyList()
    ).copy(hobbies = listOf(hobby("Reading")))

    val result = validator.validate(resume)
    assertFalse(result.isValid)
    assertHasMessage(result, "Experience Skill Level must be between 1 and 5")
    assertHasMessage(result, "Detail must be at least 10 characters")
  }

  // -------- validateList aggregation across multiple resumes --------

  @Test
  fun `validateList aggregates domainResults from multiple resumes and overall validity`() {
    val validResume = baseResume(
      skills = listOf(skill()),
      experience = listOf(
        exp(timeframeStart = LocalDate.of(2022, 1, 1), timeframeEnd = LocalDate.of(2022, 12, 31)),
        exp(timeframeStart = LocalDate.of(2023, 1, 1), timeframeEnd = null)
      ),
      sideProjects = listOf(
        exp(timeframeStart = LocalDate.of(2020, 1, 1), timeframeEnd = LocalDate.of(2020, 6, 30)),
        exp(timeframeStart = LocalDate.of(2020, 7, 2), timeframeEnd = LocalDate.of(2020, 12, 31))
      )
    )

    val invalidResume = validResume.copy(
      shortcut = shortcut(title = ofLen(4)),
      skills = emptyList(),
      experience = listOf(
        exp(timeframeStart = LocalDate.of(2023, 1, 1), timeframeEnd = LocalDate.of(2023, 6, 30)),
        exp(timeframeStart = LocalDate.of(2023, 7, 2), timeframeEnd = LocalDate.of(2023, 12, 31))
      ),
      languages = listOf(language("pl"))
    )

    val result = validator.validateList(listOf(validResume, invalidResume)) as ResumeValidatorResult

    assertFalse(result.isValid, "Combined should be invalid due to the invalid resume")
    assertEquals(14, result.domainResults.size, "Expect 7 domains per resume x 2 resumes")
    assertHasMessage(result, "Title length must be between 5 and 30")
    assertHasMessage(result, "At least one skill is required")
    assertHasMessage(result, "At least two languages are required")
    assertHasMessage(result, "Timeframes must be consecutive")
  }
}