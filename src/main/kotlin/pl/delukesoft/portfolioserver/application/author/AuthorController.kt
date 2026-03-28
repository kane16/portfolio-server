package pl.delukesoft.portfolioserver.application.author

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@RequestMapping("/author")
@RegisterReflectionForBinding(Author::class)
class AuthorController(
  private val authorFacade: AuthorFacade,
  private val logger: Logger = LoggerFactory.getLogger(AuthorController::class.java)
) {

  @PostMapping("/register")
  @AuthRequired
  fun registerAuthor(
    @RequestHeader("Authorization") token: String?
  ): Author {
    logger.info("Registering author with token: $token")
    return authorFacade.registerAuthorForContextUser()
  }

  @GetMapping
  @AuthRequired
  fun getContextAuthor(
    @RequestHeader("Authorization") token: String?,
  ): Author {
    logger.info("Retrieving author context for token: $token")
    return authorFacade.getContextAuthor()
  }


}