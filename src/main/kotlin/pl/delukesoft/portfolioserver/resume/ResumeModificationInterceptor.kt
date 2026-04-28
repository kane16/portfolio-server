package pl.delukesoft.portfolioserver.resume

import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.resume.exception.ResumeOperationNotAllowed
import pl.delukesoft.portfolioserver.resume.history.ResumeVersion
import pl.delukesoft.portfolioserver.resume.history.ResumeVersionRepository
import pl.delukesoft.portfolioserver.resume.history.ResumeVersionState
import pl.delukesoft.portfolioserver.resume.history.exception.ResumeVersionNotFound
import java.time.LocalDateTime

@Aspect
@Component
class ResumeModificationInterceptor(
  private val resumeVersionRepository: ResumeVersionRepository,
) {

  @Before("@annotation(modification) && args(.., resumeVersion)")
  fun preventModificationOnPublishedResume(modification: ResumeModification, resumeVersion: ResumeVersion) {
    val dbResumeVersion =
      resumeVersionRepository.findVersionById(resumeVersion.id!!) ?: throw ResumeVersionNotFound()
    if (dbResumeVersion.state == ResumeVersionState.PUBLISHED) {
      throw ResumeOperationNotAllowed("Cannot modify published resume")
    }
  }


  @After("@annotation(modification) && args(.., resumeVersion)")
  fun modificationCompleted(modification: ResumeModification, resumeVersion: ResumeVersion) {
    resumeVersionRepository.updateLastModified(resumeVersion.id!!, LocalDateTime.now())
  }



}