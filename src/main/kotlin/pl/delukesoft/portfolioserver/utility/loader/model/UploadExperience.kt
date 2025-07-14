package pl.delukesoft.portfolioserver.utility.loader.model

import pl.delukesoft.portfolioserver.domain.resume.model.Timespan

data class UploadExperience(
  val business: UploadBusiness,
  val position: String,
  val summary: String,
  val description: String,
  val timespan: Timespan,
  val skills: List<UploadSkill>
)
