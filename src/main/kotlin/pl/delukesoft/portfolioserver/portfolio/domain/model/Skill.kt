package pl.delukesoft.portfolioserver.portfolio.domain.model

import org.springframework.data.annotation.Id

data class Skill(
  @Id val id: Long? = null,
  val name: String,
  val description: String? = null,
  val level: Int,
)
