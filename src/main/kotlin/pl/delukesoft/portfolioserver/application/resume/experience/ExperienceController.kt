package pl.delukesoft.portfolioserver.application.resume.experience

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@Tag(name = "Resume - Experience", description = "Work experience entries within a resume")
class ExperienceController(
  private val experienceFacade: ExperienceFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/experience")
  @Operation(summary = "Add experience", description = "Add a work experience entry to a resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun addExperienceToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody experience: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return experienceFacade.addExperienceToResume(resumeId, experience)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/experience/{experienceId}")
  @Operation(summary = "Edit experience", description = "Edit a work experience entry by ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun editExperience(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("experienceId") experienceId: Long,
    @RequestBody experience: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return experienceFacade.editExperienceInResume(resumeId, experienceId, experience)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/resume/edit/{resumeId}/experience/{experienceId}")
  @Operation(summary = "Delete experience", description = "Delete a work experience entry by ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun deleteExperience(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("experienceId") experienceId: Long,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return experienceFacade.deleteExperienceFromResume(resumeId, experienceId)
  }

}