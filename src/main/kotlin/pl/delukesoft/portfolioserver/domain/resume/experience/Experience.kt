package pl.delukesoft.portfolioserver.domain.resume.experience

import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe

data class Experience(
  val business: Business,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timeframe: Timeframe,
  val skills: List<SkillExperience> = emptyList()
)