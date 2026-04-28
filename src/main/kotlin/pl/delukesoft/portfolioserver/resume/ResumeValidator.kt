package pl.delukesoft.portfolioserver.resume

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.resume.education.EducationValidator
import pl.delukesoft.portfolioserver.resume.experience.Experience
import pl.delukesoft.portfolioserver.resume.hobby.HobbyValidator
import pl.delukesoft.portfolioserver.resume.language.LanguagesValidator
import pl.delukesoft.portfolioserver.resume.shortcut.ResumeShortcutValidator
import pl.delukesoft.portfolioserver.resume.skill.Skill
import pl.delukesoft.portfolioserver.resume.validation.DomainValidationResult
import pl.delukesoft.portfolioserver.resume.validation.ResumeValidatorResult
import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.Validator

@Component
class ResumeValidator(
  val resumeShortcutValidator: ResumeShortcutValidator,
  @Qualifier("skillValidator") val skillValidator: Validator<Skill>,
  @Qualifier("jobExperienceValidator") val jobExperienceValidator: Validator<Experience>,
  @Qualifier("sideProjectsValidator") val sideProjectValidator: Validator<Experience>,
  val educationValidator: EducationValidator,
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
      DomainValidationResult.build("education", educationValidator.validateList(value.education)),
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