package pl.delukesoft.portfolioserver.application.resume.experience

import pl.delukesoft.portfolioserver.application.resume.experience.skill.SkillExperienceDTO
import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO


data class ExperienceDTO(
  val business: String,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timeframe: TimeframeDTO,
  val skills: List<SkillExperienceDTO> = emptyList()
)
