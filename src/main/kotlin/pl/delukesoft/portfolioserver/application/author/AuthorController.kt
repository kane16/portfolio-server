package pl.delukesoft.portfolioserver.application.author

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@RequestMapping("/author")
@RegisterReflectionForBinding(Author::class)
@Tag(name = "Author", description = "Author registration and retrieval")
class AuthorController(
  private val authorFacade: AuthorFacade,
  private val logger: Logger = LoggerFactory.getLogger(AuthorController::class.java)
) {

  @PostMapping("/register")
  @AuthRequired
  @Operation(summary = "Register author", description = "Register the currently authenticated user as an author")
  @SecurityRequirement(name = "Bearer Authentication")
  fun registerAuthor(
    @RequestHeader("Authorization") token: String?,
  ): Author {
    logger.info("Registering author")
    return authorFacade.registerAuthorForContextUser()
  }

  @GetMapping
  @AuthRequired
  @Operation(
    summary = "Get current author",
    description = "Retrieve the author profile for the currently authenticated user"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun getContextAuthor(
    @RequestHeader("Authorization") token: String?,
  ): Author {
    logger.info("Retrieving author")
    return authorFacade.getContextAuthor()
  }

}