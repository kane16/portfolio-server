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
import org.springframework.web.bind.annotation.*
import pl.delukesoft.authplugin.author.AuthorService
import pl.delukesoft.authplugin.security.AuthRequired

@RestController
@RequestMapping("/authors")
@RegisterReflectionForBinding(value = [PortfolioAuthorDTO::class, PortfolioAuthorRequest::class])
@Tag(name = "Authors", description = "Portfolio author profile management")
class AuthorController(
  private val authorService: AuthorService,
  private val authorMapper: PortfolioAuthorMapper
) {

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
            array = ArraySchema(schema = Schema(implementation = PortfolioAuthorDTO::class))
          )
        ]
      )
    ]
  )
  fun getAllAuthors(): List<PortfolioAuthorDTO> {
    return authorService.getAllAuthors(PORTFOLIO_APP).map { authorMapper.mapToDto(it) }
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
        content = [Content(schema = Schema(implementation = PortfolioAuthorDTO::class))]
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
  ): PortfolioAuthorDTO {
    return authorMapper.mapToDto(authorService.getContextAuthor(PORTFOLIO_APP) as PortfolioAuthor)
  }

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
        content = [Content(schema = Schema(implementation = PortfolioAuthorDTO::class))]
      ),
      ApiResponse(responseCode = "404", description = "Author not found")
    ]
  )
  fun getAuthorById(
    @Parameter(description = "Author ID", required = true)
    @PathVariable("authorId") authorId: Long
  ): PortfolioAuthorDTO {
    return authorMapper.mapToDto(authorService.getAuthorById(authorId, PORTFOLIO_APP))
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
        content = [Content(schema = Schema(implementation = PortfolioAuthorDTO::class))]
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
  ): PortfolioAuthorDTO {
    val portfolioAuthor = authorMapper.mapFromRequestToDomainAuthor(author)
    return authorMapper.mapToDto(authorService.editAuthor(PORTFOLIO_APP, portfolioAuthor) as PortfolioAuthor)
  }

  private companion object {
    const val PORTFOLIO_APP = "portfolio"
  }
}
