package pl.delukesoft.portfolioserver.resume.education

import pl.delukesoft.portfolioserver.resume.experience.timeframe.TimeframeDTO

data class EducationDTO(
  val id: Long? = null,
  val title: String,
  val institution: InstitutionDTO,
  val timeframe: TimeframeDTO,
  val fieldOfStudy: String,
  val grade: Double,
  val type: String,
  val description: String? = null,
  val externalLinks: List<String> = emptyList()
)
