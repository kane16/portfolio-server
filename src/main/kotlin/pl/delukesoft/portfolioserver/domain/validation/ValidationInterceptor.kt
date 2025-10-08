package pl.delukesoft.portfolioserver.domain.validation

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
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

  @Before("@annotation(validateDomain) && args(domain,..)")
  fun validate(validateDomain: ValidateDomain, domain: SkillDomain) {
    val validationResult: ValidationResult = skillDomainValidator.validate(domain)
    if (!validationResult.isValid) {
      throw ValidationFailedException(listOf(DomainValidationResult.build("skillDomain", validationResult)))
    }
  }

}