package pl.delukesoft.portfolioserver.resume

import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.resume.exception.ResumeNotFound
import pl.delukesoft.portfolioserver.resume.exception.ResumePublicationFailed
import pl.delukesoft.portfolioserver.security.User
import pl.delukesoft.portfolioserver.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.resume.history.*
import pl.delukesoft.portfolioserver.platform.sequence.GeneratorService
import pl.delukesoft.portfolioserver.resume.validation.ValidateShortcut

@Service
class ResumeService(
  private val resumeHistoryService: ResumeHistoryService,
  private val generatorService: GeneratorService,
  private val resumeModifyRepository: ResumeModifyRepository,
  private val resumeVersionRepository: ResumeVersionRepository,
  private val resumeHistoryRepository: ResumeHistoryRepository,
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getResumeById(id: Long, user: User?): ResumeVersion {
    log.info("Getting CV with id: $id")
    return when {
      user != null && user.roles.contains("ROLE_ADMIN") -> resumeVersionRepository.findByIdOrNull(id)
        ?: throw ResumeNotFound()

      user != null && user.roles.contains("ROLE_CANDIDATE") -> resumeVersionRepository.findResumeByIdAndUsername(
        id,
        user.username
      ) ?: throw ResumeNotFound()

      else -> throw ResumeNotFound()
    }
  }

  fun getDefaultCV(user: User?): ResumeVersion {
    log.info("Getting default Resume")
    return when {
      user != null && user.roles.contains("ROLE_CANDIDATE") -> getCandidateResume(user.username)
      else -> getDefaultApplicationResume()
    }
  }

  @ValidateShortcut
  fun addResume(shortcut: ResumeShortcut): ResumeVersion {
    val newResumeVersion = createResumeVersion(
      Resume(
        shortcut = shortcut
      )
    )
    val isResumeAdded = resumeHistoryService.addResumeToHistory(newResumeVersion)
    if (!isResumeAdded) {
      throw ResumePublicationFailed("Failed to add resume to history")
    }
    return newResumeVersion
  }

  fun editResumeShortcut(resumeVersion: ResumeVersion, shortcut: ResumeShortcut): ResumeVersion {
    resumeModifyRepository.changeShortcutInResume(shortcut, resumeVersion)
    return resumeVersionRepository.findByIdOrNull(resumeVersion.id!!) ?: throw ResumeNotFound()
  }

  @CacheEvict(cacheNames = ["portfolio"], allEntries = true)
  fun unpublishResume(resumeVersion: ResumeVersion, username: String): ResumeVersion {
    val isUnpublished = resumeHistoryService.unpublishResumeVersion(resumeVersion, username)
    if (!isUnpublished) {
      throw ResumePublicationFailed("Failed to unpublish resume")
    }
    return resumeVersionRepository.findByIdOrNull(resumeVersion.id!!) ?: throw ResumeNotFound()
  }

  private fun getCandidateResume(username: String): ResumeVersion {
    return resumeHistoryService.findByUsernameAndRole(username, "ROLE_CANDIDATE").defaultResume
      ?: throw ResumeNotFound()
  }

  private fun getDefaultApplicationResume(): ResumeVersion {
    return resumeHistoryService.findByRole("ROLE_ADMIN").defaultResume ?: throw ResumeNotFound()
  }

  @CacheEvict(cacheNames = ["portfolio"], allEntries = true)
  fun publishResume(versionToPublish: ResumeVersion, username: String): ResumeVersion {
    val isPublished = resumeHistoryService.publishResumeVersion(versionToPublish, username)
    if (!isPublished) {
      throw ResumePublicationFailed("Failed to publish resume")
    }
    return resumeVersionRepository.findByIdOrNull(versionToPublish.id!!) ?: throw ResumeNotFound()
  }

  private fun createResumeVersion(resume: Resume): ResumeVersion {
    val generatedId = generatorService.getAndIncrement("resume_version")
    val version = ResumeVersion(
      id = generatedId,
      resume = resume,
      version = (resumeHistoryRepository.findMaxVersionInResumeHistoryByUsername(resume.shortcut.user.username)
        ?: 0) + 1,
      state = ResumeVersionState.DRAFT
    )
    return resumeVersionRepository.save(version)
  }

}