package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.print.GeneratorService

@Service
class ResumeHistoryService(
  private val resumeHistoryRepository: ResumeHistoryRepository,
  private val generatorService: GeneratorService
) {

  fun findHistoryListByUsernameAndRole(username: String, role: String): List<ResumeHistory> {
    return resumeHistoryRepository.findResumeHistoryByUsernameAndRoles(username, listOf(role))
  }

  fun findHistoryListByRole(role: String): List<ResumeHistory> {
    return resumeHistoryRepository.findResumesHistoryByRoles(listOf(role))
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

}