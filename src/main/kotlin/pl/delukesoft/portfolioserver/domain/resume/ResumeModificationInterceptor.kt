package pl.delukesoft.portfolioserver.domain.resume

import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Aspect
@Component
class ResumeModificationInterceptor(
  private val resumeRepository: ResumeRepository
) {

  @After("@annotation(modification) && args(..,resume)")
  fun modificationCompleted(modification: ResumeModification, resume: Resume) {
    resumeRepository.updateLastModified(resume.id!!, LocalDateTime.now())
  }



}