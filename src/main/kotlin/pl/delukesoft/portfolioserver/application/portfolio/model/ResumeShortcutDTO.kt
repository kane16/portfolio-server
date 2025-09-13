package pl.delukesoft.portfolioserver.application.portfolio.model

import jakarta.validation.constraints.Size
import pl.delukesoft.portfolioserver.adapters.image.Image

data class ResumeShortcutDTO(
  @field:Size(message = "Title length must be between 5 and 30", min = 5, max = 30) val title: String,
  @field:Size(message = "Summary length must be between 30 and 100", min = 30, max = 100) val summary: String,
  val image: Image,
)