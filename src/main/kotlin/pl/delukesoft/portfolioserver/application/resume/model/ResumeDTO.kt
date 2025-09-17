package pl.delukesoft.portfolioserver.application.resume.model

import pl.delukesoft.portfolioserver.application.portfolio.model.LanguageDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ProjectDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.SkillPortfolioDTO

data class ResumeDTO(
  val id: Long,
  val fullname: String,
  val imageSource: String,
  val title: String,
  val summary: String,
  val skills: List<SkillPortfolioDTO>,
  val languages: List<LanguageDTO>,
  val sideProjects: List<ProjectDTO>,
  val workHistory: List<ProjectDTO>,
  val hobbies: List<String>
)