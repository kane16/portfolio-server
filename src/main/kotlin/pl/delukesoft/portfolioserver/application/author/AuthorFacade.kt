package pl.delukesoft.portfolioserver.application.author

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext

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
    return currentAuthor
  }

  fun registerAuthorForContextUser(): Author {
    return authorService.createAuthor(currentUser)
  }


}