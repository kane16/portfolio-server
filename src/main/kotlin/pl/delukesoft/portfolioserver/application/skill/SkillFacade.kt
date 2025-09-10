package pl.delukesoft.portfolioserver.application.skill

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext

@Component
class SkillFacade(
  private val skillMapper: SkillMapper,
  private val userContext: UserContext
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addDomain(name: String): Boolean {
    TODO("Not yet implemented")
  }

  fun addSkill(skill: SkillDTO): Boolean {
    skillMapper.mapToSkill(skill, currentUser.username)
  }


}