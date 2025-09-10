package pl.delukesoft.portfolioserver.domain.validation

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeValidator
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillValidator
import pl.delukesoft.portfolioserver.domain.validation.exception.ValidationFailedException

@Aspect
@Component
class ValidationInterceptor(
  val resumeValidator: ResumeValidator,
  val skillValidator: SkillValidator,
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

}