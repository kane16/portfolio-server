package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resumehistory.exception.ResumeHistoryNotFound
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class ResumeHistoryService(
  private val resumeHistoryRepository: ResumeHistoryRepository,
  private val generatorService: GeneratorService,
  private val resumeVersionRepository: ResumeVersionRepository,
) {

  fun findByUsernameAndRole(username: String, role: String): ResumeHistory {
    return resumeHistoryRepository.findResumeHistoryByUsernameAndRoles(username, listOf(role))
      ?: throw ResumeHistoryNotFound()
  }

  fun findByRole(role: String): ResumeHistory {
    return resumeHistoryRepository.findResumesHistoryByRoles(listOf(role)).firstOrNull()
      ?: throw ResumeHistoryNotFound()
  }

  fun findByUsername(username: String): ResumeHistory {
    return resumeHistoryRepository.findResumeHistoryByUsername(username) ?: throw ResumeHistoryNotFound()
  }

  fun addResumeToHistory(resumeVersion: ResumeVersion): Boolean {
    return when (resumeHistoryRepository.existsByUser_Username(resumeVersion.resume.shortcut.user.username)) {
      true -> addResumeToExistingHistory(resumeVersion)
      false -> createResumeHistoryAndAddResume(resumeVersion)
    }
  }

  fun findPublishedResumeVersion(username: String): ResumeVersion? {
    val resumeHistory = findByUsername(username)
    return resumeHistory.defaultResume
  }

  fun unpublishResumeVersion(resumeVersion: ResumeVersion, username: String): Boolean {
    return resumeVersionRepository.changeResumeStatus(resumeVersion.id!!, ResumeVersionState.DRAFT) > 0 &&
      resumeHistoryRepository.setDefaultResumeForUser(username, null) > 0
  }

  fun publishResumeVersion(resumeVersion: ResumeVersion, username: String): Boolean {
    val updatedResumeVersion = resumeVersion.copy(state = ResumeVersionState.PUBLISHED)
    return resumeHistoryRepository.setDefaultResumeForUser(
      username,
      resumeVersionRepository.save(updatedResumeVersion)
    ) > 0
  }

  private fun createResumeHistoryAndAddResume(resumeVersion: ResumeVersion): Boolean {
    val generatedId = generatorService.getAndIncrement("resume_history")
    val resumeHistory = ResumeHistory(
      id = generatedId,
      defaultResume = null,
      versions = listOf(resumeVersion),
      user = resumeVersion.resume.shortcut.user
    )
    resumeHistoryRepository.save(resumeHistory)
    return true
  }

  private fun addResumeToExistingHistory(resumeVersion: ResumeVersion): Boolean {
    return resumeHistoryRepository.addResumeHistoryVersionByUsername(
      resumeVersion.resume.shortcut.user.username,
      resumeVersion
    ) > 0
  }

  fun findVersionByIdAndUsername(portfolioVersion: Long, username: String): ResumeVersion? {
    val resumeHistory = findByUsername(username)
    return resumeHistory.versions.find { it.version == portfolioVersion }
  }

}