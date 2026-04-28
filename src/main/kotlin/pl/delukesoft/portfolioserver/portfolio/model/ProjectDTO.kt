package pl.delukesoft.portfolioserver.portfolio.model

import pl.delukesoft.portfolioserver.resume.experience.timeframe.TimeframeDTO

data class ProjectDTO(
  val position: String,
  val business: String,
  val summary: String,
  val description: String,
  val timespan: TimeframeDTO,
  val skills: List<SkillPortfolioDTO>,
)
