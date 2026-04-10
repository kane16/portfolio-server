package pl.delukesoft.portfolioserver.domain.resume

import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import pl.delukesoft.blog.image.exception.ResumeOperationNotAllowed
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersionRepository
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersionState
import pl.delukesoft.portfolioserver.domain.resumehistory.exception.ResumeVersionNotFound
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