package pl.delukesoft.portfolioserver.resume.sideProject

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.security.AuthRequired
import pl.delukesoft.portfolioserver.resume.experience.ExperienceDTO

@RestController
@Tag(name = "Resume - Side Projects", description = "Side project entries within a resume")
class SideProjectController(
  private val sideProjectFacade: SideProjectFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/sideProjects")
  @Operation(summary = "Add side project", description = "Add a side project entry to a resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun addSideProjectToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody experience: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return sideProjectFacade.addSideProjectToResume(resumeId, experience)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/sideProjects/{sideProjectId}")
  @Operation(summary = "Edit side project", description = "Edit a side project entry by ID")
  @SecurityRequirement(name = "Bearer Authentication")
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
  @Operation(summary = "Delete side project", description = "Delete a side project entry by ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun deleteSideProject(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("sideProjectId") sideProjectId: Long,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return sideProjectFacade.deleteSideProjectFromResume(resumeId, sideProjectId)
  }

}