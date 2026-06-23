package pl.delukesoft.portfolioserver.resume.author

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Portfolio author response with portfolio-specific profile information")
data class PortfolioAuthorDTO(
  @field:Schema(description = "Author ID", example = "1")
  val id: Long?,
  @field:Schema(description = "User ID from the authentication service", example = "1")
  val userId: Long,
  @field:Schema(description = "Author first name", example = "Luke")
  val firstname: String,
  @field:Schema(description = "Author last name", example = "Kane")
  val lastname: String,
  @field:Schema(description = "Author username", example = "kane16")
  val username: String,
  @field:Schema(
    description = "Portfolio-specific profile information",
    implementation = PortfolioAuthorAdditionalInfo::class
  )
  val additionalInfo: PortfolioAuthorAdditionalInfo? = null
)
