package pl.delukesoft.portfolioserver.domain.unit

import org.junit.jupiter.api.Assertions.assertTrue
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import java.time.LocalDate

open class ResumeValidatorTestBase {

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

  protected fun shortcut(
    title: String = ofLen(10),
    summary: String = ofLen(50),
    username: String = "alice"
  ) = ResumeShortcut(
    user = User(username = username, email = "$username@example.com"),
    title = title,
    summary = summary,
    image = null
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

}