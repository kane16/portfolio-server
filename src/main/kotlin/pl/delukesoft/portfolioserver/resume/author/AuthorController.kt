package pl.delukesoft.portfolioserver.resume.author

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.delukesoft.authplugin.author.Author
import pl.delukesoft.authplugin.author.AuthorService
import pl.delukesoft.authplugin.security.AuthRequired

@RestController
@RequestMapping("/authors")
@RegisterReflectionForBinding(value = [PortfolioAuthor::class, PortfolioAuthorRequest::class])
@Tag(name = "Authors", description = "Portfolio author profile management")
class AuthorController(
  private val authorService: AuthorService,
  private val authorMapper: PortfolioAuthorMapper
) {

  @AuthRequired(role = "ROLE_ADMIN", app = PORTFOLIO_APP)
  @GetMapping
  @Operation(
    summary = "Get all authors",
    description = "Retrieve all authors with portfolio-specific profile information"
  )
  @ApiResponses(
    value = [
      ApiResponse(
        responseCode = "200",
        description = "Authors successfully retrieved",
        content = [
          Content(
            array = ArraySchema(schema = Schema(implementation = Author::class))
          )
        ]
      ),
      ApiResponse(responseCode = "401", description = "Unauthorized"),
      ApiResponse(responseCode = "403", description = "Admin role required")
    ]
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun getAllAuthors(
    @Parameter(description = "Authorization token", required = true)
    @RequestHeader("Authorization") token: String?
  ): List<Author> {
    return authorService.getAllAuthors(PORTFOLIO_APP)
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = PORTFOLIO_APP)
  @GetMapping("/context")
  @Operation(
    summary = "Get current author",
    description = "Retrieve the portfolio author profile for the authenticated user"
  )
  @ApiResponses(
    value = [
      ApiResponse(
        responseCode = "200",
        description = "Current author successfully retrieved",
        content = [Content(schema = Schema(implementation = PortfolioAuthor::class))]
      ),
      ApiResponse(responseCode = "401", description = "Unauthorized"),
      ApiResponse(responseCode = "403", description = "Author role required"),
      ApiResponse(responseCode = "404", description = "Author not found")
    ]
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun getContextAuthor(
    @Parameter(description = "Authorization token", required = true)
    @RequestHeader("Authorization") token: String?
  ): PortfolioAuthor {
    return authorService.getContextAuthor(PORTFOLIO_APP) as PortfolioAuthor
  }

  @AuthRequired(role = "ROLE_ADMIN", app = PORTFOLIO_APP)
  @GetMapping("/{authorId}")
  @Operation(
    summary = "Get author by ID",
    description = "Retrieve an author by their author ID"
  )
  @ApiResponses(
    value = [
      ApiResponse(
        responseCode = "200",
        description = "Author successfully retrieved",
        content = [Content(schema = Schema(implementation = Author::class))]
      ),
      ApiResponse(responseCode = "401", description = "Unauthorized"),
      ApiResponse(responseCode = "403", description = "Admin role required"),
      ApiResponse(responseCode = "404", description = "Author not found")
    ]
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun getAuthorById(
    @Parameter(description = "Author ID", required = true)
    @PathVariable("authorId") authorId: Long,
    @Parameter(description = "Authorization token", required = true)
    @RequestHeader("Authorization") token: String?
  ): Author {
    return authorService.getAuthorById(authorId)
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = PORTFOLIO_APP)
  @PatchMapping
  @Operation(
    summary = "Edit author",
    description = "Update common author fields and portfolio-specific profile information"
  )
  @ApiResponses(
    value = [
      ApiResponse(
        responseCode = "200",
        description = "Author successfully updated",
        content = [Content(schema = Schema(implementation = PortfolioAuthor::class))]
      ),
      ApiResponse(responseCode = "400", description = "Invalid author data"),
      ApiResponse(responseCode = "401", description = "Unauthorized"),
      ApiResponse(responseCode = "403", description = "Author role required"),
      ApiResponse(responseCode = "404", description = "Author not found")
    ]
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun editAuthor(
    @Parameter(description = "Updated author data", required = true)
    @Valid @RequestBody author: PortfolioAuthorRequest,
    @Parameter(description = "Authorization token", required = true)
    @RequestHeader("Authorization") token: String?
  ): PortfolioAuthor {
    val portfolioAuthor = authorMapper.mapToApplicationAuthor(author)
    return authorService.editAuthor(PORTFOLIO_APP, portfolioAuthor) as PortfolioAuthor
  }

  @AuthRequired(role = "ROLE_ADMIN", app = PORTFOLIO_APP)
  @DeleteMapping("/{authorId}")
  @Operation(summary = "Delete author", description = "Delete an author by their author ID")
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "200", description = "Author successfully deleted"),
      ApiResponse(responseCode = "401", description = "Unauthorized"),
      ApiResponse(responseCode = "403", description = "Admin role required"),
      ApiResponse(responseCode = "404", description = "Author not found")
    ]
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun deleteAuthor(
    @Parameter(description = "Author ID", required = true)
    @PathVariable("authorId") authorId: Long,
    @Parameter(description = "Authorization token", required = true)
    @RequestHeader("Authorization") token: String?
  ) {
    authorService.deleteAuthor(authorId)
  }

  private companion object {
    const val PORTFOLIO_APP = "portfolio"
  }
}
