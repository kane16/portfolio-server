package pl.delukesoft.portfolioserver.domain.unit

import org.junit.jupiter.api.Assertions.assertTrue
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult

open class ResumeValidatorTestBase {

  protected fun anyShortcut(): ResumeShortcut = ResumeShortcut(User("", "", emptyList()), "", "")

  protected fun skill(
    name: String = "Kotlin",
    level: Int = 3,
    username: String = "alice",
    description: String? = "some",
    domains: List<SkillDomain> = emptyList()
  ) = Skill(
    id = null,
    name = name,
    level = level,
    description = description,
    username = username,
    domains = domains
  )

  protected fun domain(
    name: String = "Backend",
    username: String = "alice"
  ) = SkillDomain(
    id = null,
    name = name,
    username = username
  )

  protected fun anyLevel(): LanguageLevel =
    LanguageLevel.entries.first()

  protected fun otherLevel(): LanguageLevel =
    LanguageLevel.entries.last()

  protected fun language(
    name: String = "English",
    level: LanguageLevel = anyLevel(),
    username: String = "alice"
  ) = Language(
    id = null,
    name = name,
    level = level,
    username = username
  )

  protected fun resumeWithSkills(vararg s: Skill) = Resume(
    id = null,
    shortcut = anyShortcut(),
    skills = s.toList(),
    experience = emptyList(),
    sideProjects = emptyList(),
    hobbies = emptyList(),
    languages = emptyList()
  )

  protected fun resumeWithLanguages(vararg languages: Language) = Resume(
    id = null,
    shortcut = anyShortcut(),
    skills = emptyList(),
    experience = emptyList(),
    sideProjects = emptyList(),
    hobbies = emptyList(),
    languages = languages.toList(),
    createdOn = java.time.LocalDateTime.now(),
    lastModified = java.time.LocalDateTime.now()
  )

  protected fun messages(result: ValidationResult): List<String> =
    result.errors.map { it }

  protected fun assertHasMessage(result: ValidationResult, expected: String) {
    assertTrue(
      messages(result).any { it == expected || expected in it },
      "Expected error message \"$expected\". Got: ${messages(result)}"
    )
  }

}