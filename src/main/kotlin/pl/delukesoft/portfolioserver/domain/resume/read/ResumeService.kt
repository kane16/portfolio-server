package pl.delukesoft.portfolioserver.domain.resume.read

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.CurriculumNotFound
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.template.model.PortfolioSearchDTO
import pl.delukesoft.portfolioserver.domain.resume.read.model.Resume

@Service
class ResumeService(
  private val resumeReadRepository: ResumeReadRepository,
  private val userContext: UserContext
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getCvById(id: Long, portfolioSearch: PortfolioSearchDTO): Resume {
    log.info("Getting CV with id: $id")
    val contextUser = userContext.user!!
    val resume = when {
      contextUser.roles.contains("ROLE_ADMIN") ->  resumeReadRepository.findResumeById(id) ?: throw CurriculumNotFound()
      contextUser.roles.contains("ROLE_CANDIDATE") -> resumeReadRepository.findResumeByIdAndUserId(id, contextUser.id) ?: throw CurriculumNotFound()
      else -> throw CurriculumNotFound()
    }
    if (portfolioSearch != null) {
      val (text, business, skills) = portfolioSearch
      return resume.copy(skills = resume.skills.filter { !it.name.contains(text!!) })
    }
    return resume
  }

  fun getDefaultCV(): Resume {
    log.info("Getting default CV")
    if (userContext.user != null && userContext.user?.roles?.contains("ROLE_CANDIDATE") == true) {
      return getUserResume(resumeReadRepository.findResumeByUserIdAndRoles(userContext.user?.id!!, listOf("ROLE_CANDIDATE")))
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