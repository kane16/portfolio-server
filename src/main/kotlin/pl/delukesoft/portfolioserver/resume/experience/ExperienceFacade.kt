package pl.delukesoft.portfolioserver.resume.experience

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.security.UserContext
import pl.delukesoft.portfolioserver.resume.ResumeMapper
import pl.delukesoft.portfolioserver.resume.ResumeService
import pl.delukesoft.portfolioserver.resume.experience.ExperienceService

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
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val resume = resumeVersion.resume
    val resumeSkills = resume.skills
    val experienceToAdd = resumeMapper.mapDTOToExperience(experience, resumeSkills)
    return experienceService.addExperienceToResume(experienceToAdd, resumeVersion)
  }

  fun editExperienceInResume(resumeId: Long, experienceId: Long, experience: ExperienceDTO): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val resume = resumeVersion.resume
    val resumeSkills = resume.skills
    val experienceToEdit = resumeMapper.mapDTOToExperience(experience, resumeSkills).copy(id = experienceId)
    return experienceService.editResume(experienceToEdit, resumeVersion)
  }

  fun deleteExperienceFromResume(resumeId: Long, experienceId: Long): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    return experienceService.deleteExperienceFromResume(experienceId, resumeVersion)
  }

}