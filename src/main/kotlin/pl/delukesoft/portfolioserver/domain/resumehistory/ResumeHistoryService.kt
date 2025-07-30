package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.Resume
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

  fun addResumeToHistory(resume: Resume): Boolean {
    return when (resumeHistoryRepository.existsByUser_Username(resume.shortcut.user.username)) {
      true -> addResumeToExistingHistory(resume)
      false -> createResumeHistoryAndAddResume(resume)
    }
  }

  private fun createResumeHistoryAndAddResume(resume: Resume): Boolean {
    val generatedId = generatorService.getAndIncrement("resume_history")
    val firstVersion = createResumeVersion(resume, true)
    val resumeHistory = ResumeHistory(
      id = generatedId,
      defaultResume = firstVersion,
      versions = listOf(firstVersion),
      user = resume.shortcut.user
    )
    resumeHistoryRepository.save(resumeHistory)
    return true
  }

  private fun addResumeToExistingHistory(resume: Resume): Boolean {
    return resumeHistoryRepository.addResumeHistoryVersionByUsername(
      resume.shortcut.user.username,
      createResumeVersion(resume)
    ) > 0
  }

  private fun createResumeVersion(resume: Resume, isFirst: Boolean = false): ResumeVersion {
    val generatedId = generatorService.getAndIncrement("resume_version")
    val version = when (isFirst) {
      true -> ResumeVersion(
        id = generatedId,
        resume = resume,
        version = 1,
        state = ResumeVersionState.PUBLISHED
      )

      else -> ResumeVersion(
        id = generatedId,
        resume = resume,
        version = resumeHistoryRepository.findMaxVersionInResumeHistoryByUsername(resume.shortcut.user.username) + 1,
        state = ResumeVersionState.DRAFT
      )
    }
    return resumeVersionRepository.save(version)
  }

  fun isInitialized(): Boolean {
    return !resumeHistoryRepository.findResumeHistoryByRoles(listOf("ROLE_ADMIN")).isEmpty()
  }

}