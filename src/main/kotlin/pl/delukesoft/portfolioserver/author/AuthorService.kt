package pl.delukesoft.portfolioserver.author

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.security.User
import pl.delukesoft.portfolioserver.resume.skill.domain.SkillDomain
import pl.delukesoft.portfolioserver.resume.skill.domain.exception.SkillDomainExistsException
import pl.delukesoft.portfolioserver.platform.sequence.GeneratorService

@Service
class AuthorService(
  private val authorRepository: AuthorRepository,
  private val generatorService: GeneratorService
) {

  fun getAuthorByUsername(username: String): Author? {
    return authorRepository.findByUsername(username)
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

  fun updateAuthor(dbAuthor: Author, editAuthor: Author): Author {
    val authorToUpdate = dbAuthor.copy(
      firstname = editAuthor.firstname,
      lastname = editAuthor.lastname,
      email = editAuthor.email,
    )
    return authorRepository.save(authorToUpdate)
  }

}