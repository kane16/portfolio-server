package pl.delukesoft.portfolioserver.application.resume.experience

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.resume.ResumeMapper
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.experience.ExperienceService

@Component
class ExperienceFacade(
  private val resumeService: ResumeService,
  private val resumeMapper: ResumeMapper,
  private val experienceService: ExperienceService,
  private val userContext: UserContext
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addExperienceToResume(resumeId: Long, experience: ExperienceDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resume.skills
    val experienceToAdd = resumeMapper.mapExperienceDTOToResume(experience, resumeSkills)
    return experienceService.addExperienceToResume(experienceToAdd, resume)
  }

  fun editExperienceInResume(resumeId: Long, experienceId: Long, experience: ExperienceDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resume.skills
    val experienceToEdit = resumeMapper.mapExperienceDTOToResume(experience, resumeSkills).copy(id = experienceId)
    return experienceService.editResume(experienceToEdit, resume)
  }

  fun deleteExperienceFromResume(resumeId: Long, experienceId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    return experienceService.deleteExperienceFromResume(experienceId, resume)
  }

}