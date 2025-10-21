package pl.delukesoft.portfolioserver.application.resume.skill

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.ResumeFacade

@RestController
class SkillController(
  private val skillFacade: SkillFacade,
  private val resumeFacade: ResumeFacade
) {

  private val log = LoggerFactory.getLogger(this::class.java)

  @GetMapping("/skills")
  @AuthRequired("ROLE_CANDIDATE")
  fun getUserSkills(
    @RequestHeader("Authorization") token: String?
  ): List<SkillDTO> {
    return skillFacade.getSkills()
  }

  @GetMapping("/skills/domains")
  @AuthRequired("ROLE_CANDIDATE")
  fun getDomains(
    @RequestHeader("Authorization") token: String?
  ): List<String> {
    return skillFacade.getSkillDomains()
  }

  @GetMapping("/skills/resume/{resumeId}")
  @AuthRequired("ROLE_CANDIDATE")
  fun getSkillsByResumeId(
    @PathVariable("resumeId") resumeId: Long,
    @RequestHeader("Authorization") token: String?
  ): List<SkillDTO> {
    return resumeFacade.findSkillsByResumeId(resumeId)
  }

  @PostMapping("/skills/domains")
  @ResponseStatus(HttpStatus.CREATED)
  @AuthRequired("ROLE_CANDIDATE")
  fun addSkillDomain(
    @RequestBody name: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return skillFacade.addDomain(name)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/skills")
  @ResponseStatus(HttpStatus.CREATED)
  fun addSkillToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to add skill to portfolio")
    return resumeFacade.addSkillToResume(resumeId, skill)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/resume/edit/{resumeId}/skills/{skillName}")
  fun deleteSkill(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("skillName") skillName: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return resumeFacade.deleteSkillFromResume(resumeId, skillName)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/skills/{skillName}")
  fun editSkill(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("skillName") skillName: String,
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return resumeFacade.editSkillWithName(resumeId, skillName, skill)
  }

}