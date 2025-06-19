package pl.delukesoft.portfolioserver.portfolio.domain.model

import org.springframework.data.annotation.Id

data class Business(
  @Id val id: Long? = null,
  val name: String,
)
