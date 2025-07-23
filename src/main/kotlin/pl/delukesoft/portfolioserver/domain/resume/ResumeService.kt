package pl.delukesoft.portfolioserver.domain.resume

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.ResumeExistsException
import pl.delukesoft.blog.image.exception.ResumeNotFound
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService
import java.time.LocalDateTime

@Service
class ResumeService(
  private val resumeRepository: ResumeRepository,
  private val resumeHistoryService: ResumeHistoryService,
  private val generatorService: GeneratorService
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getCvById(id: Long, user: User?): Resume {
    log.info("Getting CV with id: $id")
    return when {
      user != null && user.roles.contains("ROLE_ADMIN") -> resumeRepository.findResumeById(id) ?: throw ResumeNotFound()
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

  fun addResume(newResume: Resume, unsafe: Boolean = false): Resume {
    var resumeToSave = newResume.copy(
      skills = emptyList(),
      experience = emptyList(),
      sideProjects = emptyList(),
      languages = emptyList(),
      hobbies = emptyList()
    )
    if (newResume.id == null) {
      return if(!unsafe) save(resumeToSave) else save(newResume)
    }
    val existingResume = resumeRepository.findResumeById(newResume.id) ?: throw ResumeNotFound()
    throw ResumeExistsException(existingResume.id!!)
  }

  fun updateResume(resume: Resume): Resume {
    if (resume.id == null) {
      throw ResumeNotFound()
    }
    val resumeToUpdate = resumeRepository.findResumeById(resume.id)?.copy(
      title = resume.title,
      summary = resume.summary,
      image = resume.image,
      lastModified = LocalDateTime.now()
    ) ?: throw ResumeNotFound()
    return save(resumeToUpdate)
  }

  private fun save(resume: Resume): Resume {
    if (resume.id == null) {
      val generatedId = generatorService.getAndIncrement("resume")
      return resumeRepository.save(resume.copy(id = generatedId))
    }
    return resumeRepository.save(resume)
  }

  private fun getCandidateResume(username: String): Resume {
    return resumeHistoryService.findByUsernameAndRole(username, "ROLE_CANDIDATE").defaultResume
  }

  private fun getDefaultApplicationResume(): Resume {
    return resumeHistoryService.findByRole("ROLE_ADMIN").defaultResume
  }

}