package pl.delukesoft.portfolioserver.domain.resume.experience

import org.springframework.data.annotation.Id
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe

data class Experience(
  @Id val id: Long? = null,
  val business: Business,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timeframe: Timeframe,
  val skills: List<SkillExperience> = emptyList()
)