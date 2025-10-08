package pl.delukesoft.portfolioserver.domain.resume

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.ResumeExistsException
import pl.delukesoft.blog.image.exception.ResumeNotFound
import pl.delukesoft.blog.image.exception.ResumeOperationNotAllowed
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService
import pl.delukesoft.portfolioserver.domain.validation.Validator
import java.time.LocalDateTime

@Service
class ResumeService(
  private val resumeRepository: ResumeRepository,
  private val resumeHistoryService: ResumeHistoryService,
  private val generatorService: GeneratorService,
  @Qualifier("jobExperienceValidator") private val experienceValidator: Validator<Experience>,
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getResumeById(id: Long, user: User?): Resume {
    log.info("Getting CV with id: $id")
    return when {
      user != null && user.roles.contains("ROLE_ADMIN") -> resumeRepository.findResumeById(id) ?: throw ResumeNotFound()
      user != null && user.roles.contains("ROLE_CANDIDATE") -> resumeRepository.findResumeByIdAndUsername(
        id,
        user.username
      ) ?: throw ResumeNotFound()

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

  fun addResume(newResume: Resume): Resume {
    if (newResume.id == null) {
      val dbResume = save(newResume)
      resumeHistoryService.addResumeToHistory(dbResume)
      return dbResume
    }
    val existingResume = resumeRepository.findResumeById(newResume.id) ?: throw ResumeNotFound()
    throw ResumeExistsException(existingResume.id!!)
  }

  fun editResumeShortcut(id: Long, shortcut: ResumeShortcut): Boolean {
    if (!resumeRepository.existsById(id)) {
      throw ResumeNotFound()
    }
    updateResume(
      Resume(
        id = id,
        shortcut = shortcut,
      )
    )
    return true
  }

  fun updateResume(resume: Resume): Resume {
    if (resume.id == null) {
      throw ResumeNotFound()
    }
    val resumeToUpdate = resumeRepository.findResumeById(resume.id)?.copy(
      shortcut = resume.shortcut.copy(
        title = resume.shortcut.title,
        summary = resume.shortcut.summary,
        image = resume.shortcut.image,
      ),
      lastModified = LocalDateTime.now()
    ) ?: throw ResumeNotFound()
    return save(resumeToUpdate)
  }

  fun unpublishResume(resumeVersion: ResumeVersion, username: String): Boolean {
    return resumeHistoryService.unpublishResumeVersion(resumeVersion, username)
  }

  private fun save(resume: Resume): Resume {
    if (resume.id == null) {
      val generatedId = generatorService.getAndIncrement("resume")
      return resumeRepository.save(resume.copy(id = generatedId))
    }
    return resumeRepository.save(resume)
  }

  private fun getCandidateResume(username: String): Resume {
    return resumeHistoryService.findByUsernameAndRole(username, "ROLE_CANDIDATE").defaultResume?.resume!!
  }

  private fun getDefaultApplicationResume(): Resume {
    return resumeHistoryService.findByRole("ROLE_ADMIN").defaultResume?.resume!!
  }

  fun publishResume(versionToPublish: ResumeVersion, username: String): Boolean {
    return resumeHistoryService.publishResumeVersion(versionToPublish, username)
  }

  fun addSkillToResume(resume: Resume, skillToAdd: Skill): Boolean {
    if (resume.skills.any { it.name == skillToAdd.name }) {
      throw ResumeOperationNotAllowed("Skill '${skillToAdd.name}' already exists in resume")
    }
    return resumeRepository.addSkillToResume(resume.id!!, skillToAdd) > 0
  }

  fun deleteSkillFromResume(resume: Resume, skillToRemove: Skill): Boolean {
    return resumeRepository.deleteSkillFromResume(resume.id!!, skillToRemove) > 0
  }

  fun editSkill(resume: Resume, skillToEdit: Skill, skillUpdate: Skill): Boolean {
    return resumeRepository.updateSkill(resume.id!!, skillToEdit.name, skillUpdate) == 1L
  }

}