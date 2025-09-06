package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.experience.ExperienceValidator
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyValidator
import pl.delukesoft.portfolioserver.domain.resume.language.LanguagesValidator
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcutValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillValidator
import pl.delukesoft.portfolioserver.domain.validation.DomainValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ResumeValidatorResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class ResumeValidator(
  val resumeShortcutValidator: ResumeShortcutValidator,
  val skillValidator: SkillValidator,
  @Qualifier("jobExperienceValidator") val jobExperienceValidator: ExperienceValidator,
  @Qualifier("sideProjectsValidator") val sideProjectValidator: ExperienceValidator,
  val hobbyValidator: HobbyValidator,
  val languagesValidator: LanguagesValidator,
) : Validator<Resume>() {

  override fun validate(value: Resume): ValidationResult {
    return validateResume(value)
  }

  private fun validateResume(value: Resume): ResumeValidatorResult {
    val domainValidationResults = listOf(
      DomainValidationResult.build("shortcut", resumeShortcutValidator.validate(value.shortcut)),
      DomainValidationResult.build("skill", skillValidator.validateList(value.skills)),
      DomainValidationResult.build("experience", jobExperienceValidator.validateList(value.experience)),
      DomainValidationResult.build("sideProject", sideProjectValidator.validateList(value.sideProjects)),
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