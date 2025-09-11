package pl.delukesoft.portfolioserver.application.skill

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@RequestMapping("/skills")
class SkillController(private val skillFacade: SkillFacade) {

  @PostMapping("/domains/{name}")
  @ResponseStatus(HttpStatus.CREATED)
  @AuthRequired("ROLE_CANDIDATE", "ROLE_ADMIN")
  fun addSkillDomain(
    @PathVariable("name") name: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return skillFacade.addDomain(name)
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @AuthRequired("ROLE_CANDIDATE", "ROLE_ADMIN")
  fun addSkill(
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return skillFacade.addSkill(skill)
  }

}