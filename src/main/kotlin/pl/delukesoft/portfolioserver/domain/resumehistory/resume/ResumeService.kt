package pl.delukesoft.portfolioserver.domain.resumehistory.resume

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.CurriculumNotFound
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.template.model.PortfolioSearch
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.print.GeneratorService

@Service
class ResumeService(
  private val resumeRepository: ResumeRepository,
  private val resumeHistoryService: ResumeHistoryService,
  private val userContext: UserContext,
  private val generatorService: GeneratorService
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): Resume {
    log.info("Getting CV with id: $id")
    return when {
      userContext.user != null && userContext.user!!.roles.contains("ROLE_ADMIN") -> resumeRepository.findResumeById(id) ?: throw CurriculumNotFound()
      else -> throw CurriculumNotFound()
    }
  }

  fun getDefaultCV(): Resume {
    log.info("Getting default Resume")
    return when {
      userContext.user != null && userContext.user!!.roles.contains("ROLE_CANDIDATE") -> getCandidateResume(userContext.user!!.username)
      else -> getDefaultApplicationResume()
    }
  }

  private fun getCandidateResume(username: String): Resume {
    return getUserResume(resumeHistoryService.findHistoryListByUsernameAndRole(username, "ROLE_CANDIDATE").map { it.defaultResume })
  }

  private fun getDefaultApplicationResume(): Resume {
    return getUserResume(resumeHistoryService.findHistoryListByRole("ROLE_ADMIN").map { it.defaultResume })
  }

  fun getUserResume(resumes: List<Resume>): Resume {
    return when(resumes.size) {
      1 -> resumes[0]
      else -> throw CurriculumNotFound()
    }
  }

  fun saveAll(resumes: List<Resume>): List<Resume> =
      resumes.map { save(it) }

    fun save(resume: Resume): Resume {
      if (resume.id == null) {
        val generatedId = generatorService.getAndIncrement("resume")
        return resumeRepository.save(resume.copy(id = generatedId))
      }
      TODO("Not yet implemented")
    }

}