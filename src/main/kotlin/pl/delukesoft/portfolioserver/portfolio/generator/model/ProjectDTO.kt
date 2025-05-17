package pl.delukesoft.portfolioserver.portfolio.generator.model

data class ProjectDTO(
  val name: String,
  val timespan: TimespanDTO,
  val description: String,
  val skills: List<String>,
) {

}
