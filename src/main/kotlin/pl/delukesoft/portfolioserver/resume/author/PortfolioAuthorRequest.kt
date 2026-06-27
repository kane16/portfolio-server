package pl.delukesoft.portfolioserver.resume.author

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PortfolioAuthorRequest(
  @field:Valid
  @field:NotNull(message = "Author is required")
  val author: AuthorRequest?,
  @field:Valid
  @field:NotNull(message = "Additional info is required")
  val additionalInfo: PortfolioAuthorAdditionalInfo?
)

data class AuthorRequest(
  val id: Long?,
  @field:NotNull(message = "Author user ID is required")
  @field:Positive(message = "Author user ID should be positive")
  val userId: Long,
  @field:NotBlank(message = "Author firstname shouldn't be blank")
  val firstname: String,
  @field:NotBlank(message = "Author lastname shouldn't be blank")
  val lastname: String,
  @field:NotBlank(message = "Author username shouldn't be blank")
  val username: String
)
