package pl.delukesoft.portfolioserver.domain.author

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.author.exception.AuthorNotFound
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.exception.SkillDomainExistsException
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class AuthorService(
  private val authorRepository: AuthorRepository,
  private val generatorService: GeneratorService
) {

  fun getAuthor(user: User): Author? {
    return authorRepository.findByUsername(user.username)
  }

  fun createAuthor(user: User): Author {
    val author = Author(
      username = user.username,
      email = user.email,
      id = generatorService.getAndIncrement("author")
    )
    return authorRepository.save(author)
  }

  fun getMainAuthor(): Author {
    return authorRepository.findByRolesContains("ROLE_ADMIN") ?: throw AuthorNotFound()
  }

  fun addSkillToAuthor(skillToAdd: Skill, author: Author): Boolean {
    authorRepository.addSkillToAuthor(author.username, skillToAdd)
    return true
  }

  fun addDomainToAuthor(domainToAdd: SkillDomain, author: Author): Boolean {
    if (authorRepository.addDomainToAuthor(author.username, domainToAdd) == 0) {
      throw SkillDomainExistsException(domainToAdd.name)
    }
    return true
  }

}