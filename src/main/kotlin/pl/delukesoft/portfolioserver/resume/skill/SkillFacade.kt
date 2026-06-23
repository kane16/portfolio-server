package pl.delukesoft.portfolioserver.resume.skill

import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.author.AuthorService
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthor
import pl.delukesoft.portfolioserver.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.resume.skill.domain.exception.SkillDomainExistsException

@Component
class SkillFacade(
  private val skillMapper: SkillMapper,
  private val authContext: AuthContext,
  private val authorService: AuthorService,
) {

  private val currentAuthor
    get() = requireNotNull(authContext.author as PortfolioAuthor) { "Authenticated author is required" }

  fun addDomain(name: String): PortfolioAuthor {
    if (getSkillDomains().contains(name)) {
      throw SkillDomainExistsException(name)
    }
    val updatedAdditionalInfo = currentAuthor.additionalInfo.copy(
      domains = currentAuthor.additionalInfo.domains + SkillDomain(name)
    )
    val authorWithEdit = PortfolioAuthor(
      id = authContext.author.authorId,
      userId = authContext.author.authorUserId,
      username = authContext.author.authorUsername,
      firstname = authContext.author.authorFirstname,
      lastname = authContext.author.authorLastname,
      additionalInfo = updatedAdditionalInfo
    )
    return authorService.editAuthor("portfolio", authorWithEdit) as PortfolioAuthor
  }

  fun getSkills(): List<SkillDTO> {
    return currentAuthor.additionalInfo.skills.map { skillMapper.mapToDTO(it) }
  }

  fun getSkillDomains(): List<String> {
    return currentAuthor.additionalInfo.domains.map { it.name }
  }


}
