package pl.delukesoft.portfolioserver.adapters.image

import jakarta.validation.constraints.NotNull

data class Image(
  @field:NotNull(message = "Image name must not be empty") val name: String,
  @field:NotNull(message = "Src value must not be empty") val src: String
)
