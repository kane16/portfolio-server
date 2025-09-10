package pl.delukesoft.portfolioserver.application.skill

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/skills")
class SkillController(private val skillFacade: SkillFacade) {

  @PostMapping("/domains/{name}")
  fun addSkillDomain(
    @PathVariable("name") name: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return skillFacade.addDomain(name)
  }

  @PostMapping
  fun addSkill(
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return skillFacade.addSkill(skill)
  }

}