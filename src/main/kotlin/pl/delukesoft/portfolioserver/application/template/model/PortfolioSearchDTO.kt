package pl.delukesoft.portfolioserver.application.template.model

import jakarta.validation.constraints.Size

data class PortfolioSearchDTO(
  @field:Size(message = "Text cannot have more than 10 signs", max = 10) val text: String? = null,
  val business: List<String> = listOf(),
  val skills: List<String> = listOf(),
)