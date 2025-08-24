package pl.delukesoft.portfolioserver.domain.validation

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.Resume

@Aspect
@Component
class ValidationInterceptor {

  @Before("@annotation(validateResume) && args(resume,..)")
  fun validate(validateResume: ValidateResume, resume: Resume) {

  }

}