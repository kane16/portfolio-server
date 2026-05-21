package pl.delukesoft.portfolioserver.resume.experience

import pl.delukesoft.portfolioserver.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.resume.skill.SkillDTO


data class ExperienceDTO(
  val id: Long? = null,
  val business: String,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timespan: TimeframeDTO,
  val skills: List<SkillDTO> = emptyList()
)
