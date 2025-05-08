package pl.delukesoft.portfolioserver.portfolio.model

import org.springframework.data.annotation.Id

data class Business(
  @Id val id: Long? = null,
  val name: String,
)
