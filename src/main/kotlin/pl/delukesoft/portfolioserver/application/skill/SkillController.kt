package pl.delukesoft.portfolioserver.application.skill

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.ResumeFacade

@RestController
@RequestMapping("/skills")
class SkillController(
  private val skillFacade: SkillFacade,
  private val resumeFacade: ResumeFacade
) {

  @GetMapping
  @AuthRequired("ROLE_CANDIDATE")
  fun getUserSkills(
    @RequestHeader("Authorization") token: String?
  ): List<SkillDTO> {
    return skillFacade.getSkills()
  }

  @GetMapping("/domains")
  @AuthRequired("ROLE_CANDIDATE")
  fun getDomains(
    @RequestHeader("Authorization") token: String?
  ): List<String> {
    return skillFacade.getSkillDomains()
  }

  @GetMapping("/resume/{resumeId}")
  @AuthRequired("ROLE_CANDIDATE")
  fun getSkillsByResumeId(
    @PathVariable("resumeId") resumeId: Long,
    @RequestHeader("Authorization") token: String?
  ): List<SkillDTO> {
    return resumeFacade.findSkillsByResumeId(resumeId)
  }

  @PostMapping("/domains")
  @ResponseStatus(HttpStatus.CREATED)
  @AuthRequired("ROLE_CANDIDATE")
  fun addSkillDomain(
    @RequestBody name: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return skillFacade.addDomain(name)
  }

}