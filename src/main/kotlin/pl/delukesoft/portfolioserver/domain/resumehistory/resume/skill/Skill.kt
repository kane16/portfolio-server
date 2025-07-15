package pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Skill")
data class Skill(
  @Id val id: Long? = null,
  val name: String,
  val level: Int,
  val description: String? = null,
)
