package pl.delukesoft.portfolioserver.domain.resume.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

data class Experience(
  @Id val id: Long? = null,
  @DBRef(lazy = false) val business: Business,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timespan: Timespan,
  val skills: List<SkillExperience> = emptyList()
)
