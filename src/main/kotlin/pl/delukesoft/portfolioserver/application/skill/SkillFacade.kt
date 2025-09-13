package pl.delukesoft.portfolioserver.application.skill

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillService
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainService

@Component
class SkillFacade(
  private val skillMapper: SkillMapper,
  private val skillDomainService: SkillDomainService,
  private val userContext: UserContext,
  private val skillService: SkillService
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addDomain(name: String): Boolean {
    val domainToAdd = skillMapper.mapToSkillDomain(name, currentUser.username, emptyList())
    skillDomainService.addDomain(domainToAdd)
    return true
  }

  fun getSkill(name: String): Skill {
    return skillService.getByName(name, currentUser.username)
  }

  fun addSkill(skill: SkillDTO): Boolean {
    val availableDomains = skillDomainService.getUserDomains(currentUser.username)
    val skillToAdd = skillMapper.mapToSkill(skill, currentUser.username, availableDomains)
    skillService.addSkill(skillToAdd)
    return true
  }


}