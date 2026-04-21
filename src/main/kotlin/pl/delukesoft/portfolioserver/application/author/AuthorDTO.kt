package pl.delukesoft.portfolioserver.application.author

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthorDTO(
  @field:NotBlank(message = "Firstname must not be empty")
  @field:Size(max = 100, message = "Firstname must not exceed 100 characters")
  val firstname: String,
  @field:NotBlank(message = "Lastname must not be empty")
  @field:Size(max = 100, message = "Lastname must not exceed 100 characters")
  val lastname: String,
  @field:NotBlank(message = "Email must not be empty")
  @field:Email(message = "Email must be a valid address")
  val email: String
)
