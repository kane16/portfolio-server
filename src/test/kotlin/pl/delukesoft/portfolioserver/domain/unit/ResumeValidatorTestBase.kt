package pl.delukesoft.portfolioserver.domain.unit

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.constraint.ConstraintRepository
import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService
import pl.delukesoft.portfolioserver.domain.constraint.FieldConstraint
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.education.Education
import pl.delukesoft.portfolioserver.domain.resume.education.EducationInstitution
import pl.delukesoft.portfolioserver.domain.resume.education.EducationType
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.shortcut.contact.ContactInfo
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import java.time.LocalDate

open class ResumeValidatorTestBase {

  private val constraintRepositoryMock = mockk<ConstraintRepository>()
  protected val constraintService = ConstraintService(constraintRepositoryMock)

  protected fun anyLevel(): LanguageLevel =
    LanguageLevel.entries.first()

  protected fun otherLevel(): LanguageLevel =
    LanguageLevel.entries.last()

  protected fun tf(start: LocalDate, end: LocalDate? = null) = Timeframe(start = start, end = end)

  protected fun language(
    name: String = "English",
    level: LanguageLevel = anyLevel(),
  ) = Language(
    name = name,
    level = level
  )

  protected fun skill(
    name: String = "Kotlin",
    level: Int = 3,
    description: String? = "some",
    domains: List<SkillDomain> = emptyList()
  ) = Skill(
    name = name,
    level = level,
    description = description,
    domains = domains
  )

  protected fun se(
    skill: Skill = skill(),
    level: Int = 3,
    detail: String = "Experienced with coroutines"
  ) = SkillExperience(skill = skill, level = level, detail = detail)

  protected fun ofLen(n: Int, ch: Char = 'a') = buildString { repeat(n) { append(ch) } }

  protected fun business(name: String) =
    Business(name = name)

  protected fun domain(
    name: String = "Backend",
  ) = SkillDomain(
    name = name,
  )

  protected fun hobby(name: String) =
    Hobby(name = name)

  protected fun messages(result: ValidationResult): List<String> =
    result.errors.map { it }

  protected fun assertHasMessage(result: ValidationResult, expected: String) {
    assertTrue(
      messages(result).any { it == expected || expected in it },
      "Expected error message \"$expected\". Got: ${messages(result)}"
    )
  }

  protected fun contactInfo(
    email: String = "alice@example.com",
    phone: String = "+1234567890",
    location: String = "San Francisco, CA",
    linkedin: String = "https://linkedin.com/in/alice",
    github: String = "https://github.com/alice",
    timezone: String = "PST"
  ) = ContactInfo(
    email = email,
    phone = phone,
    location = location,
    linkedin = linkedin,
    github = github,
    timezone = timezone
  )

  protected fun shortcut(
    title: String = ofLen(10),
    summary: String = ofLen(50),
    username: String = "alice",
    contact: ContactInfo? = contactInfo()
  ) = ResumeShortcut(
    user = User(username = username, email = "$username@example.com"),
    title = title,
    summary = summary,
    image = null,
    contact = contact
  )

  protected fun exp(
    position: String = "Backend Dev",
    summary: String = ofLen(60),
    timeframeStart: LocalDate = LocalDate.of(2022, 1, 1),
    timeframeEnd: LocalDate? = LocalDate.of(2022, 12, 31),
    skills: List<SkillExperience> = listOf(se(level = 3, detail = "Solid production experience")),
  ): Experience = Experience(
    business = business(name = "Acme"),
    position = position,
    summary = summary,
    description = null,
    timeframe = tf(start = timeframeStart, end = timeframeEnd),
    skills = skills
  )

  // ----- Education helpers -----
  protected fun eduInstitution(
    name: String = "Uni",
    city: String = "City",
    country: String = "Country"
  ) = EducationInstitution(name = name, city = city, country = country)

  protected fun education(
    title: String = ofLen(10),
    institution: EducationInstitution = eduInstitution(),
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

  protected fun baseResume(
    skills: List<Skill> = listOf(skill()),
    experience: List<Experience> = emptyList(),
    sideProjects: List<Experience> = emptyList()
  ): Resume = Resume(
    shortcut = shortcut(),
    skills = skills,
    experience = experience,
    sideProjects = sideProjects,
    education = listOf(education()),
    hobbies = listOf(hobby("Chess")),
    languages = listOf(language("English"), language("German"))
  )

  init {
    every { constraintRepositoryMock.findCachedConstraints() }.answers {
      listOf(
        FieldConstraint.build(
          path = "resume.education.title",
          minLength = 5,
          maxLength = 30
        ),
        FieldConstraint.build(
          path = "resume.shortcut.title",
          minLength = 5,
          maxLength = 30
        ),
        FieldConstraint.build(
          path = "resume.language.name",
          minLength = 3,
          maxLength = 50
        ),
        FieldConstraint.build(
          "resume.skill.description",
          3,
          100
        ),
        FieldConstraint.build(
          "resume.skill.name",
          minLength = 1,
          maxLength = 50
        ),
        FieldConstraint.build(
          "resume.skill.domain.name",
          minLength = 1,
          maxLength = 50
        ),
        FieldConstraint.build(
          "resume.experience.business.name",
          minLength = 3,
          maxLength = 50
        ),
        FieldConstraint.build(
          "resume.shortcut.summary",
          minLength = 30,
          maxLength = 1000
        ),
        FieldConstraint.build(
          "resume.experience.skill.detail",
          minLength = 3,
          maxLength = 50
        ),
        FieldConstraint.build(
          "resume.hobby.name",
          minLength = 3,
          maxLength = 50
        ),
        FieldConstraint.build(
          "resume.experience.summary",
          minLength = 5,
          maxLength = 1000
        ),
        FieldConstraint.build(
          "resume.education.fieldOfStudy",
          minLength = 3,
          maxLength = 50
        ),
        FieldConstraint.build(
          "resume.experience.position",
          minLength = 3,
          maxLength = 50
        ),
        FieldConstraint.build(
          "resume.experience.description"
        ),
        FieldConstraint.build(
          path = "resume.shortcut.contact.email",
          minLength = 5,
          maxLength = 50
        ),
        FieldConstraint.build(
          path = "resume.shortcut.contact.phone",
          minLength = 5,
          maxLength = 20
        ),
        FieldConstraint.build(
          path = "resume.shortcut.contact.location",
          minLength = 3,
          maxLength = 100
        ),
        FieldConstraint.build(
          path = "resume.shortcut.contact.linkedin",
          minLength = 5,
          maxLength = 100
        ),
        FieldConstraint.build(
          path = "resume.shortcut.contact.github",
          minLength = 5,
          maxLength = 100
        ),
        FieldConstraint.build(
          path = "resume.shortcut.contact.timezone",
          minLength = 2,
          maxLength = 50
        )
      )
    }
  }

}