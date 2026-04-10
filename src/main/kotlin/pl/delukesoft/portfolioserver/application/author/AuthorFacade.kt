package pl.delukesoft.portfolioserver.application.author

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.author.exception.AuthorExistsException
import pl.delukesoft.portfolioserver.application.author.exception.AuthorNotFound

@Component
class AuthorFacade(
  private val authorService: AuthorService,
  private val userContext: UserContext
) {

  private val currentAuthor
    get() = requireNotNull(userContext.author) { "Authenticated author is required" }

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun getContextAuthor(): Author {
    return authorService.getAuthor(currentUser) ?: throw AuthorNotFound()
  }

  @Transactional
  fun registerAuthorForContextUser(): Author {
    if (authorService.getAuthor(currentUser) != null) {
      throw AuthorExistsException()
    }
    return authorService.createAuthor(currentUser)
  }

}