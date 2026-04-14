package pl.delukesoft.portfolioserver.application.resume.education

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@Tag(name = "Resume - Education", description = "Education entries within a resume")
class EducationController(
  private val educationFacade: EducationFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/education")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Add education", description = "Add an education entry to a resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun addEducationToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody education: EducationDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return educationFacade.addEducationToResume(resumeId, education)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/education/{educationId}")
  @Operation(summary = "Edit education", description = "Edit an education entry by ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun editEducationById(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("educationId") educationId: Long,
    @RequestBody education: EducationDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return educationFacade.modifyEducationInResume(resumeId, education, educationId)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/resume/edit/{resumeId}/education/{educationId}")
  @Operation(summary = "Delete education", description = "Delete an education entry by ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun deleteEducationById(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("educationId") educationId: Long,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return educationFacade.deleteEducationFromResume(resumeId, educationId)
  }

}