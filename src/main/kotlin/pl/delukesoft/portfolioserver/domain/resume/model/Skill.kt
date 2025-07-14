package pl.delukesoft.portfolioserver.domain.resume.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collation = "Skill")
data class Skill(
  @Id val id: Long? = null,
  val name: String,
  val level: Int,
  val description: String? = null,
)
