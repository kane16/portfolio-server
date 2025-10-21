package pl.delukesoft.portfolioserver.application.resume.sideProject

import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO

@RestController
class SideProjectController(
  private val sideProjectFacade: SideProjectFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/sideProjects")
  fun addSideProjectToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody experience: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return sideProjectFacade.addSideProjectToResume(resumeId, experience)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/sideProjects/{sideProjectId}")
  fun editSideProject(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("sideProjectId") sideProjectId: Long,
    @RequestBody experience: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return sideProjectFacade.editSideProjectInResume(resumeId, sideProjectId, experience)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/resume/edit/{resumeId}/sideProjects/{sideProjectId}")
  fun deleteSideProject(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("sideProjectId") sideProjectId: Long,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return sideProjectFacade.deleteSideProjectFromResume(resumeId, sideProjectId)
  }

}