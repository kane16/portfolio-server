package pl.delukesoft.portfolioserver.application.resume.experience

import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
class ExperienceController(
  private val experienceFacade: ExperienceFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/experience")
  fun addExperienceToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody experience: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return experienceFacade.addExperienceToResume(resumeId, experience)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/experience/{experienceId}")
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
  fun deleteExperience(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("experienceId") experienceId: Long,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return experienceFacade.deleteExperienceFromResume(resumeId, experienceId)
  }

}