package pl.delukesoft.portfolioserver.resume.skill

import com.fasterxml.jackson.databind.ObjectMapper
import pl.delukesoft.authplugin.author.Author
import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.author.AuthorService
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.portfolioserver.resume.author.PortfolioApplicationAuthor
import pl.delukesoft.portfolioserver.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.resume.skill.domain.exception.SkillDomainExistsException

@Component
class SkillFacade(
  private val skillMapper: SkillMapper,
  private val authContext: AuthContext<PortfolioApplicationAuthor>,
  private val authorService: AuthorService,
  private val objectMapper: ObjectMapper,
) {

  private val currentAuthor
    get() = requireNotNull(authContext.author) { "Authenticated author is required" }

  fun addDomain(name: String): PortfolioApplicationAuthor {
    if (getSkillDomains().contains(name)) {
      throw SkillDomainExistsException(name)
    }
    val updatedAdditionalInfo = currentAuthor.additionalInfo.copy(
      domains = currentAuthor.additionalInfo.domains + SkillDomain(name)
    )
    val authorWithEdit = PortfolioApplicationAuthor(
      author = Author(
        currentAuthor.author.userId,
        currentAuthor.author.firstname,
        currentAuthor.author.lastname,
        currentAuthor.author.username,
        objectMapper.valueToTree(updatedAdditionalInfo)
      ),
      additionalInfo = updatedAdditionalInfo
    )
    return authorService.editAuthor("portfolio", authorWithEdit) as PortfolioApplicationAuthor
  }

  fun getSkills(): List<SkillDTO> {
    return currentAuthor.additionalInfo.skills.map { skillMapper.mapToDTO(it) }
  }

  fun getSkillDomains(): List<String> {
    return currentAuthor.additionalInfo.domains.map { it.name }
  }


}
