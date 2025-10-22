package pl.delukesoft.portfolioserver.application.resume.education

import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
class EducationController(
  private val educationFacade: EducationFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/education")
  fun addEducationToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody education: EducationDTO
  ): Boolean {
    return educationFacade.addEducationToResume(resumeId, education)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/education/{educationId}")
  fun editEducationById(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("educationId") educationId: Long,
    @RequestBody education: EducationDTO
  ): Boolean {
    return educationFacade.modifyEducationInResume(resumeId, education, educationId)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/resume/edit/{resumeId}/education/{educationId}")
  fun deleteEducationById(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("educationId") educationId: Long
  ): Boolean {
    return educationFacade.deleteEducationFromResume(resumeId, educationId)
  }

}