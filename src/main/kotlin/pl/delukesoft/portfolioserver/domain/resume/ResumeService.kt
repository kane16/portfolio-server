package pl.delukesoft.portfolioserver.domain.resume

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.CurriculumNotFound
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService

@Service
class ResumeService(
  private val resumeRepository: ResumeRepository,
  private val resumeHistoryService: ResumeHistoryService,
  private val userContext: UserContext,
  private val generatorService: GeneratorService
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getCvById(id: Long): Resume {
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
    return resumeHistoryService.findByUsernameAndRole(username, "ROLE_CANDIDATE").defaultResume
  }

  private fun getDefaultApplicationResume(): Resume {
    return resumeHistoryService.findByRole("ROLE_ADMIN").defaultResume
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