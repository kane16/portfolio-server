package pl.delukesoft.portfolioserver.domain.validation

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeValidator
import pl.delukesoft.portfolioserver.domain.resume.education.Education
import pl.delukesoft.portfolioserver.domain.resume.education.EducationValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyValidator
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.language.LanguagesValidator
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.domain.validation.exception.ValidationFailedException

@Aspect
@Component
class ValidationInterceptor(
  private val resumeValidator: ResumeValidator,
  private val skillValidator: Validator<Skill>,
  private val skillDomainValidator: Validator<SkillDomain>,
  @Qualifier("jobExperienceValidator") private val experienceValidator: Validator<Experience>,
  @Qualifier("sideProjectsValidator") private val sideProjectValidator: Validator<Experience>,
  private val hobbyValidator: HobbyValidator,
  private val languagesValidator: LanguagesValidator,
  private val shortcutValidator: Validator<ResumeShortcut>,
  private val educationValidator: EducationValidator,
) {

  @Before("@annotation(validateResume) && args(resume,..)")
  fun validate(validateResume: ValidateResume, resume: Resume) {
    val validationResult: ResumeValidatorResult = resumeValidator.validate(resume) as ResumeValidatorResult
    if (!validationResult.isValid) {
      throw ValidationFailedException(validationResult.domainResults)
    }
  }

  @Before("@annotation(validateSkill) && args(skill,..)")
  fun validate(validateSkill: ValidateSkill, skill: Skill) {
    val validationResult: ValidationResult = skillValidator.validate(skill)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("skill", validationResult)))
    }
  }

  @Before("@annotation(validateSkill) && args(skills,..)")
  fun validate(validateSkill: ValidateSkill, skills: List<Skill>) {
    val validationResult: ValidationResult = skillValidator.validateList(skills)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("skill", validationResult)))
    }
  }

  @Before("@annotation(validateDomain) && args(domain,..)")
  fun validate(validateDomain: ValidateDomain, domain: SkillDomain) {
    val validationResult: ValidationResult = skillDomainValidator.validate(domain)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("skillDomain", validationResult)))
    }
  }

  @Before("@annotation(validateExperiences) && args(experiences,..)")
  fun validate(validateExperiences: ValidateExperiences, experiences: List<Experience>) {
    val validationResult: ValidationResult = experienceValidator.validateList(experiences)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("experience", validationResult)))
    }
  }

  @Before("@annotation(validateHobbies) && args(hobbies,..)")
  fun validate(validateHobbies: ValidateHobbies, hobbies: List<Hobby>) {
    val validationResult: ValidationResult = hobbyValidator.validateList(hobbies)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("hobby", validationResult)))
    }
  }

  @Before("@annotation(validateLanguages) && args(languages,..))")
  fun validate(validateLanguages: ValidateLanguages, languages: List<Language>) {
    val validationResult: ValidationResult = languagesValidator.validateListForEditOperation(languages)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("language", validationResult)))
    }
  }

  @Before("@annotation(validateShortcut) && args(shortcut,..)")
  fun validate(validateShortcut: ValidateShortcut, shortcut: ResumeShortcut) {
    val validationResult: ValidationResult = shortcutValidator.validate(shortcut)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("shortcut", validationResult)))
    }

  }

  @Before("@annotation(validateEducation) && args(education,..)")
  fun validate(validateEducation: ValidateEducation, education: List<Education>) {
    val validationResult: ValidationResult = educationValidator.validateList(education)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("education", validationResult)))
    }
  }

  @Before("@annotation(validateSideProjects) && args(sideProject,..)")
  fun validate(validateSideProjects: ValidateSideProjects, sideProject: List<Experience>) {
    val validationResult: ValidationResult = sideProjectValidator.validateList(sideProject)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("sideProject", validationResult)))
    }
  }

}