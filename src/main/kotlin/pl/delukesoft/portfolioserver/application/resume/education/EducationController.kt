package pl.delukesoft.portfolioserver.application.resume.education

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
class EducationController(
  private val educationFacade: EducationFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/education")
  @ResponseStatus(HttpStatus.CREATED)
  fun addEducationToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody education: EducationDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return educationFacade.addEducationToResume(resumeId, education)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/education/{educationId}")
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
  fun deleteEducationById(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("educationId") educationId: Long,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return educationFacade.deleteEducationFromResume(resumeId, educationId)
  }

}