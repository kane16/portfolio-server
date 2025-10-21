package pl.delukesoft.portfolioserver.application.resume.hobby

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
class HobbyController(
  private val hobbyFacade: HobbyFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/hobbies")
  @ResponseStatus(HttpStatus.CREATED)
  fun addHobby(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody hobby: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return hobbyFacade.addHobbyToResume(resumeId, hobby)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/resume/edit/{resumeId}/hobbies")
  fun deleteHobbyFromResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody hobby: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return hobbyFacade.deleteHobbyFromResume(resumeId, hobby)
  }

}