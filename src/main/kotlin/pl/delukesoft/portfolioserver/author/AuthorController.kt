package pl.delukesoft.portfolioserver.author

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.security.AuthRequired

@RestController
@RequestMapping("/authors")
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

  @PostMapping("/register/users/{userId}")
  @AuthRequired("ROLE_ADMIN")
  @Operation(summary = "Register author for user", description = "Register an author for a specific user by user ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun registerAuthorForUser(
    @PathVariable userId: Long,
    @RequestBody @Valid author: AuthorDTO,
    @RequestHeader("Authorization") token: String?,
  ): Author {
    logger.info("Registering author for user with ID: $userId")
    return authorFacade.registerAuthorForUser(token!!, userId, author)
  }

  @PutMapping("/edit/users/{userId}")
  @AuthRequired("ROLE_ADMIN")
  @Operation(summary = "Edit author for user", description = "Edit an author for a specific user by user ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun editAuthorForUser(
    @PathVariable userId: Long,
    @RequestBody @Valid author: AuthorDTO,
    @RequestHeader("Authorization") token: String?,
  ): Author {
    logger.info("Editing author for user with ID: $userId")
    return authorFacade.editAuthorForUser(token!!, userId, author)
  }

  @GetMapping
  @AuthRequired("ROLE_ADMIN")
  @Operation(summary = "Get all authors", description = "Retrieve all registered authors")
  @SecurityRequirement(name = "Bearer Authentication")
  fun getAllAuthors(
    @RequestHeader("Authorization") token: String?,
  ): List<Author> {
    logger.info("Retrieving all authors")
    return authorFacade.getAllAuthors()
  }

  @GetMapping("context")
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