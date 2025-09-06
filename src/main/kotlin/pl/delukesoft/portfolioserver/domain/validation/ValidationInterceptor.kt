package pl.delukesoft.portfolioserver.domain.validation

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeValidator
import pl.delukesoft.portfolioserver.domain.validation.exception.ValidationFailedException

@Aspect
@Component
class ValidationInterceptor(
  val resumeValidator: ResumeValidator
) {

  @Before("@annotation(validateResume) && args(resume,..)")
  fun validate(validateResume: ValidateResume, resume: Resume) {
    val validationResult: ResumeValidatorResult = resumeValidator.validate(resume) as ResumeValidatorResult
    if (!validationResult.isValid) {
      throw ValidationFailedException(validationResult.domainResults)
    }
  }

}