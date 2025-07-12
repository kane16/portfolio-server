package pl.delukesoft.portfolioserver.domain.resume.model

import org.springframework.data.annotation.Id

data class Experience(
  @Id val id: Long? = null,
  val business: Business,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timespan: Timespan,
  val skills: List<Skill> = emptyList(),
  val isSideProject: Boolean = false,
)
