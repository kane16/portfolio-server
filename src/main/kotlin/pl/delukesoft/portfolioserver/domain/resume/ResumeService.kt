package pl.delukesoft.portfolioserver.domain.resume

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.ResumeNotFound
import pl.delukesoft.blog.image.exception.ResumeOperationNotAllowed
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService
import pl.delukesoft.portfolioserver.domain.validation.ValidateShortcut

@Service
class ResumeService(
  private val resumeRepository: ResumeRepository,
  private val resumeHistoryService: ResumeHistoryService,
  private val generatorService: GeneratorService,
  private val resumeModifyRepository: ResumeModifyRepository
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getResumeById(id: Long, user: User?): Resume {
    log.info("Getting CV with id: $id")
    return when {
      user != null && user.roles.contains("ROLE_ADMIN") -> resumeRepository.findResumeById(id) ?: throw ResumeNotFound()
      user != null && user.roles.contains("ROLE_CANDIDATE") -> resumeRepository.findResumeByIdAndUsername(
        id,
        user.username
      ) ?: throw ResumeNotFound()

      else -> throw ResumeNotFound()
    }
  }

  fun getDefaultCV(user: User?): Resume {
    log.info("Getting default Resume")
    return when {
      user != null && user.roles.contains("ROLE_CANDIDATE") -> getCandidateResume(user.username)
      else -> getDefaultApplicationResume()
    }
  }

  @ValidateShortcut
  fun addResume(shortcut: ResumeShortcut): Resume {
    val dbResume = resumeRepository.save(
      Resume(
        id = generatorService.getAndIncrement("resume"),
        shortcut = shortcut
      )
    )
    resumeHistoryService.addResumeToHistory(dbResume)
    return dbResume
  }

  fun editResumeShortcut(resume: Resume, shortcut: ResumeShortcut): Boolean {
    return resumeModifyRepository.changeShortcutInResume(shortcut, resume)
  }

  fun unpublishResume(resumeVersion: ResumeVersion, username: String): Boolean {
    return resumeHistoryService.unpublishResumeVersion(resumeVersion, username)
  }

  private fun getCandidateResume(username: String): Resume {
    return resumeHistoryService.findByUsernameAndRole(username, "ROLE_CANDIDATE").defaultResume?.resume!!
  }

  private fun getDefaultApplicationResume(): Resume {
    return resumeHistoryService.findByRole("ROLE_ADMIN").defaultResume?.resume!!
  }

  fun publishResume(versionToPublish: ResumeVersion, username: String): Boolean {
    return resumeHistoryService.publishResumeVersion(versionToPublish, username)
  }

  fun markResumeReadyForPublishing(resume: Resume): Boolean {
    if (resume.isReadyToPublish) {
      throw ResumeOperationNotAllowed("Resume is ready for publishing - cannot redo the operation")
    }
    return resumeModifyRepository.markResumeReadyForPublication(resume)
  }

  fun unmarkResumeReadyForPublish(resume: Resume): Boolean {
    if (!resume.isReadyToPublish) {
      throw ResumeOperationNotAllowed("Resume is not ready for publishing - cannot redo the operation")
    }
    return resumeModifyRepository.unmarkResumeReadyForPublication(resume)
  }

}