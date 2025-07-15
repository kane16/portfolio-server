package pl.delukesoft.portfolioserver.domain.resumehistory.resume.experience

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.experience.skill.SkillExperience
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.Timespan

data class Experience(
  @Id val id: Long? = null,
  @DBRef(lazy = false) val business: Business,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timespan: Timespan,
  val skills: List<SkillExperience> = emptyList()
)