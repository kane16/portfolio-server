package pl.delukesoft.portfolioserver.resume.skill

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.security.UserContext
import pl.delukesoft.portfolioserver.author.AuthorService

@Component
class SkillFacade(
  private val skillMapper: SkillMapper,
  private val userContext: UserContext,
  private val authorService: AuthorService,
) {

  private val currentAuthor
    get() = requireNotNull(userContext.author) { "Authenticated author is required" }

  fun addDomain(name: String): Boolean {
    val domainToAdd = skillMapper.mapToSkillDomain(name, currentAuthor.domains)
    return authorService.addDomainToAuthor(domainToAdd, currentAuthor)
  }

  fun getSkills(): List<SkillDTO> {
    return currentAuthor.skills.map { skillMapper.mapToDTO(it) }
  }

  fun getSkillDomains(): List<String> {
    return currentAuthor.domains.map { it.name }
  }


}