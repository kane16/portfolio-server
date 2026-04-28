package pl.delukesoft.portfolioserver.resume.sideProject

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.security.UserContext
import pl.delukesoft.portfolioserver.resume.ResumeMapper
import pl.delukesoft.portfolioserver.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.resume.ResumeService
import pl.delukesoft.portfolioserver.resume.sideProject.SideProjectService

@Component
class SideProjectFacade(
  private val sideProjectService: SideProjectService,
  private val resumeService: ResumeService,
  private val resumeMapper: ResumeMapper,
  private val userContext: UserContext
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addSideProjectToResume(resumeId: Long, experience: ExperienceDTO): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val resume = resumeVersion.resume
    val resumeSkills = resume.skills
    val sideProjectToAdd = resumeMapper.mapDTOToExperience(experience, resumeSkills)
    return sideProjectService.addSideProjectToResume(sideProjectToAdd, resumeVersion)
  }

  fun editSideProjectInResume(resumeId: Long, sideProjectId: Long, experience: ExperienceDTO): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resumeVersion.resume.skills
    val sideProjectToEdit = resumeMapper.mapDTOToExperience(experience, resumeSkills).copy(id = sideProjectId)
    return sideProjectService.editResume(sideProjectToEdit, resumeVersion)
  }

  fun deleteSideProjectFromResume(resumeId: Long, sideProjectId: Long): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    return sideProjectService.deleteExperienceFromResume(sideProjectId, resumeVersion)
  }

}