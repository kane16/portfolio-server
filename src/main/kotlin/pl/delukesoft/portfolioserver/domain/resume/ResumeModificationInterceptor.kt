package pl.delukesoft.portfolioserver.domain.resume

import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import pl.delukesoft.blog.image.exception.ResumeOperationNotAllowed
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersionRepository
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersionState
import pl.delukesoft.portfolioserver.domain.resumehistory.exception.ResumeVersionNotFound
import java.time.LocalDateTime

@Aspect
@Component
class ResumeModificationInterceptor(
  private val resumeRepository: ResumeRepository,
  private val resumeVersionRepository: ResumeVersionRepository
) {

  @Before("@annotation(modification) && args(..,resume)")
  fun preventModificationOnPublishedResume(modification: ResumeModification, resume: Resume) {
    val resumeVersion =
      resumeVersionRepository.findResumeVersionsByResumeId(resume.id!!) ?: throw ResumeVersionNotFound()
    if (resumeVersion.state == ResumeVersionState.PUBLISHED) {
      throw ResumeOperationNotAllowed("Cannot modify published resume")
    }
  }


  @After("@annotation(modification) && args(..,resume)")
  fun modificationCompleted(modification: ResumeModification, resume: Resume) {
    resumeRepository.updateLastModified(resume.id!!, LocalDateTime.now())
  }



}