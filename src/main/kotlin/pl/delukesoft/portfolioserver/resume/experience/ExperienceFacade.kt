package pl.delukesoft.portfolioserver.resume.experience

import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.portfolioserver.resume.ResumeMapper
import pl.delukesoft.portfolioserver.resume.ResumeService

@Component
class ExperienceFacade(
  private val resumeService: ResumeService,
  private val resumeMapper: ResumeMapper,
  private val experienceService: ExperienceService,
  private val authContext: AuthContext
) {

  private val currentUser
    get() = requireNotNull(authContext.user) { "Authenticated user is required" }

  fun addExperienceToResume(resumeId: Long, experience: ExperienceDTO): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val resume = resumeVersion.resume
    val resumeSkills = resume.skills
    val experienceToAdd = resumeMapper.mapDTOToExperience(experience, resumeSkills)
    return experienceService.addExperienceToResume(experienceToAdd, resumeVersion)
  }

  fun editExperienceInResume(
    resumeId: Long,
    experienceId: Long,
    experience: ExperienceDTO
  ): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val resume = resumeVersion.resume
    val resumeSkills = resume.skills
    val experienceToEdit =
      resumeMapper.mapDTOToExperience(experience, resumeSkills).copy(id = experienceId)
    return experienceService.editResume(experienceToEdit, resumeVersion)
  }

  fun deleteExperienceFromResume(resumeId: Long, experienceId: Long): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    return experienceService.deleteExperienceFromResume(experienceId, resumeVersion)
  }

}