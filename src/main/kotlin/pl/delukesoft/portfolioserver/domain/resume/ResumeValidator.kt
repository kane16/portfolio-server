package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.experience.ExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyValidator
import pl.delukesoft.portfolioserver.domain.resume.language.LanguagesValidator
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ShortcutValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillValidator
import pl.delukesoft.portfolioserver.domain.validation.DomainValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ResumeValidatorResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class ResumeValidator(
  val shortcutValidator: ShortcutValidator,
  val skillValidator: SkillValidator,
  val experienceValidator: ExperienceValidator,
  val hobbyValidator: HobbyValidator,
  val languagesValidator: LanguagesValidator,
) : Validator<Resume>() {

  override fun validate(value: Resume): ValidationResult {
    return validateResume(value)
  }

  private fun validateResume(value: Resume): ResumeValidatorResult {
    val domainValidationResults = listOf(
      DomainValidationResult.build("shortcut", shortcutValidator.validate(value.shortcut)),
      DomainValidationResult.build("skill", skillValidator.validateList(value.skills)),
      DomainValidationResult.build("experience", experienceValidator.validateList(value.experience)),
      DomainValidationResult.build("sideProject", experienceValidator.validateList(value.sideProjects)),
      DomainValidationResult.build("hobby", hobbyValidator.validateList(value.hobbies)),
      DomainValidationResult.build("language", languagesValidator.validateList(value.languages))
    )

    return ResumeValidatorResult(
      domainValidationResults.all { it.isValid },
      domainValidationResults
    )
  }

  override fun validateList(values: List<Resume>): ValidationResult {
    val resumeValidationResults = values.map { validateResume(it) }
    return ResumeValidatorResult(
      resumeValidationResults.all { it.isValid },
      resumeValidationResults.flatMap { it.domainResults }
    )
  }

}