package pl.delukesoft.portfolioserver.application.skill

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@RequestMapping("/skills")
class SkillController(private val skillFacade: SkillFacade) {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @AuthRequired("ROLE_CANDIDATE")
  fun addSkill(
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return skillFacade.addSkill(skill)
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