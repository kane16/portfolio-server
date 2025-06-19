package pl.delukesoft.portfolioserver.portfolio.domain.model

import org.springframework.data.annotation.Id

data class Experience(
  @Id val id: Long? = null,
  val business: Business,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timespan: Timespan,
  val skills: List<Skill> = emptyList(),
)
