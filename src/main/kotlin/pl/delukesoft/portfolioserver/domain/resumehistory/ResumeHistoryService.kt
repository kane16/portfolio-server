package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resumehistory.exception.ResumeHistoryNotFound
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class ResumeHistoryService(
  private val resumeHistoryRepository: ResumeHistoryRepository,
  private val generatorService: GeneratorService
) {

  fun findByUsernameAndRole(username: String, role: String): ResumeHistory {
    return resumeHistoryRepository.findResumeHistoryByUsernameAndRoles(username, listOf(role)) ?: throw ResumeHistoryNotFound()
  }

  fun findByRole(role: String): ResumeHistory {
    return resumeHistoryRepository.findResumesHistoryByRoles(listOf(role)).firstOrNull() ?: throw ResumeHistoryNotFound()
  }

  fun findByUsername(username: String): ResumeHistory {
    return resumeHistoryRepository.findResumeHistoryByUsername(username) ?: throw ResumeHistoryNotFound()
  }

  fun saveAll(resumeHistories: List<ResumeHistory>): List<ResumeHistory> {
    return resumeHistories.map { save(it) }
  }

  fun save(resumeHistory: ResumeHistory): ResumeHistory {
    if (resumeHistory.id == null) {
      val id = generatorService.getAndIncrement("resumeHistory")
      return resumeHistoryRepository.save(resumeHistory.copy(id = id))
    }
    TODO("Not yet implemented")
  }

  fun isInitialized(): Boolean {
    return !resumeHistoryRepository.findResumeHistoryByRoles(listOf("ROLE_ADMIN")).isEmpty()
  }

}