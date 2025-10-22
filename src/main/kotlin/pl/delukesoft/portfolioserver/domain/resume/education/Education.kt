package pl.delukesoft.portfolioserver.domain.resume.education

import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe

data class Education(
  val id: Long? = null,
  val title: String,
  val institution: EducationInstitution,
  val timeframe: Timeframe,
  val fieldOfStudy: String,
  val grade: Double,
  val type: EducationType? = EducationType.DEGREE,
  val description: String? = null,
  val externalLinks: List<String> = emptyList()
)
