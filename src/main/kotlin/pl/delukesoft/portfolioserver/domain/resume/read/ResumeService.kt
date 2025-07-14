package pl.delukesoft.portfolioserver.domain.resume.read

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.CurriculumNotFound
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.template.model.PortfolioSearch
import pl.delukesoft.portfolioserver.domain.resume.model.Resume

@Service
class ResumeService(
  private val resumeReadRepository: ResumeReadRepository,
  private val userContext: UserContext
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): Resume {
    log.info("Getting CV with id: $id")
    val contextUser = userContext.user!!
    return when {
      contextUser.roles.contains("ROLE_ADMIN") ->  resumeReadRepository.findResumeById(id) ?: throw CurriculumNotFound()
      contextUser.roles.contains("ROLE_CANDIDATE") -> resumeReadRepository.findResumeByIdAndUsername(id, contextUser.username) ?: throw CurriculumNotFound()
      else -> throw CurriculumNotFound()
    }
  }

  fun getDefaultCV(): Resume {
    log.info("Getting default CV")
    if (userContext.user != null && userContext.user?.roles?.contains("ROLE_CANDIDATE") == true) {
      return getUserResume(resumeReadRepository.findResumeByUsernameAndRoles(userContext.user?.username!!, listOf("ROLE_CANDIDATE")))
    }
    return getUserResume(resumeReadRepository.findResumesByRoles(listOf("ROLE_ADMIN")))
  }

  fun getUserResume(resumes: List<Resume>): Resume {
    return when(resumes.size) {
      1 -> resumes[0]
      else -> throw CurriculumNotFound()
    }
  }

}