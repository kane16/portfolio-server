package pl.delukesoft.portfolioserver.resume.hobby

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.security.AuthRequired

@RestController
@Tag(name = "Resume - Hobbies", description = "Hobby entries within a resume")
class HobbyController(
  private val hobbyFacade: HobbyFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/hobbies")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Add hobby", description = "Add a hobby to a resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun addHobby(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody hobby: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return hobbyFacade.addHobbyToResume(resumeId, hobby)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/resume/edit/{resumeId}/hobbies")
  @Operation(summary = "Delete hobby", description = "Delete a hobby from a resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun deleteHobbyFromResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody hobby: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return hobbyFacade.deleteHobbyFromResume(resumeId, hobby)
  }

}