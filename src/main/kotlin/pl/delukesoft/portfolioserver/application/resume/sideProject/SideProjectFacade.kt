package pl.delukesoft.portfolioserver.application.resume.sideProject

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.resume.ResumeMapper
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.sideProject.SideProjectService

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
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resume.skills
    val sideProjectToAdd = resumeMapper.mapExperienceDTOToResume(experience, resumeSkills)
    return sideProjectService.addSideProjectToResume(sideProjectToAdd, resume)
  }

  fun editSideProjectInResume(resumeId: Long, sideProjectId: Long, experience: ExperienceDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resume.skills
    val sideProjectToEdit = resumeMapper.mapExperienceDTOToResume(experience, resumeSkills).copy(id = sideProjectId)
    return sideProjectService.editResume(sideProjectToEdit, resume)
  }

  fun deleteSideProjectFromResume(resumeId: Long, sideProjectId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    return sideProjectService.deleteExperienceFromResume(sideProjectId, resume)
  }

}