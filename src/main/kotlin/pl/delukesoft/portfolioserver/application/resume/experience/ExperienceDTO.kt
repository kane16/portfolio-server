package pl.delukesoft.portfolioserver.application.resume.experience

import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO


data class ExperienceDTO(
  val id: Long? = null,
  val business: String,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timespan: TimeframeDTO,
  val skills: List<SkillDTO> = emptyList()
)
