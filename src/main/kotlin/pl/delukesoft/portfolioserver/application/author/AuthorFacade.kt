package pl.delukesoft.portfolioserver.application.author

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequestService
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.adapters.auth.exception.UserNotFoundException
import pl.delukesoft.portfolioserver.application.author.exception.AuthorExistsException
import pl.delukesoft.portfolioserver.application.author.exception.AuthorNotFound

@Component
class AuthorFacade(
  private val authorService: AuthorService,
  private val userContext: UserContext,
  private val authRequestService: AuthRequestService
) {

  private val currentAuthor
    get() = requireNotNull(userContext.author) { "Authenticated author is required" }

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun getContextAuthor(): Author {
    return authorService.getAuthorByUsername(currentUser.username) ?: throw AuthorNotFound()
  }

  @Transactional
  fun registerAuthorForContextUser(): Author {
    if (authorService.getAuthorByUsername(currentUser.username) != null) {
      throw AuthorExistsException()
    }
    return authorService.createAuthor(currentUser)
  }

  @Transactional
  fun registerAuthorForUser(token: String, userId: Long, author: AuthorDTO): Author {
    val userForAuthor = authRequestService.getUserById(token, userId) ?: throw UserNotFoundException(userId)
    val existingAuthorForUser = authorService.getAuthorByUsername(userForAuthor.username)
    if (existingAuthorForUser != null) {
      throw AuthorExistsException()
    }
    val authorToSave = Author(
      username = userForAuthor.username,
      email = author.email,
      firstname = author.firstname,
      lastname = author.lastname,
      roles = userForAuthor.roles
    )
    return authorService.createAuthor(authorToSave)
  }

  fun editAuthorForUser(token: String, userId: Long, author: AuthorDTO): Author {
    val userForAuthor = authRequestService.getUserById(token, userId) ?: throw UserNotFoundException(userId)
    val dbAuthor = authorService.getAuthorByUsername(userForAuthor.username) ?: throw AuthorNotFound()
    val editAuthor = Author(
      username = userForAuthor.username,
      email = author.email,
      firstname = author.firstname,
      lastname = author.lastname,
    )
    return authorService.updateAuthor(dbAuthor, editAuthor)
  }

  fun getAllAuthors(): List<Author> {
    return authorService.getAuthors()
  }

}