package pl.delukesoft.portfolioserver.pdf.model

data class ProjectDTO(
  val position: String,
  val business: String,
  val summary: String,
  val description: String,
  val timespan: TimespanDTO,
  val skills: List<SkillDTO>,
) {

}
