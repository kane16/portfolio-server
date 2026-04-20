package pl.delukesoft.portfolioserver.application.author

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.adapters.auth.User
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

  fun getAuthors(): List<Author> = authorRepository.findAll()

  fun createAuthor(user: User): Author {
    val author = Author(
      firstname = user.firstname,
      lastname = user.lastname,
      username = user.username,
      email = user.email,
      id = generatorService.getAndIncrement("author")
    )
    return authorRepository.save(author)
  }

  fun createAuthor(author: Author): Author {
    val authorToSave = author.copy(id = generatorService.getAndIncrement("author"))
    return authorRepository.save(authorToSave)
  }

  fun addDomainToAuthor(domainToAdd: SkillDomain, author: Author): Boolean {
    if (authorRepository.addDomainToAuthor(author.username, domainToAdd) == 0) {
      throw SkillDomainExistsException(domainToAdd.name)
    }
    return true
  }

}