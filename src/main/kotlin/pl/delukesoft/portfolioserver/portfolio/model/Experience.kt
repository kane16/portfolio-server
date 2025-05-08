package pl.delukesoft.portfolioserver.portfolio.model

import org.springframework.data.annotation.Id

data class Experience(
  @Id val id: Long? = null,
  val business: Business,
  val position: String,
  val shortDescription: String,
  val description: String? = null,
)
