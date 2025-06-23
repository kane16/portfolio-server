package pl.delukesoft.portfolioserver.domain.resume.read.model

import org.springframework.data.annotation.Id

data class Skill(
  @Id val id: Long? = null,
  val name: String,
  val description: String? = null,
  val level: Int,
)
