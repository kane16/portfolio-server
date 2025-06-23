package pl.delukesoft.portfolioserver.domain.resume.read.model

import org.springframework.data.annotation.Id

data class Business(
  @Id val id: Long? = null,
  val name: String,
)
