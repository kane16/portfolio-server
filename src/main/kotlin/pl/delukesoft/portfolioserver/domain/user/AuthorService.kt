package pl.delukesoft.portfolioserver.domain.user

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.exception.SkillDomainExistsException
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService
import pl.delukesoft.portfolioserver.domain.user.exception.AuthorNotFound

@Service
class AuthorService(
  private val authorRepository: AuthorRepository,
  private val generatorService: GeneratorService
) {

  fun getAuthorWithAddIfNotExists(user: User): Author {
    return authorRepository.findByUsername(user.username) ?: authorRepository.save(
      Author(
        id = generatorService.getAndIncrement("author"),
        username = user.username,
        roles = user.roles,
        email = user.email,
      )
    )
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