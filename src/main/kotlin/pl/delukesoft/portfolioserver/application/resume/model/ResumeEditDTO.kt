package pl.delukesoft.portfolioserver.application.resume.model

import pl.delukesoft.portfolioserver.application.portfolio.model.LanguageDTO
import pl.delukesoft.portfolioserver.application.resume.education.EducationDTO
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO

data class ResumeEditDTO(
  val id: Long,
  val fullname: String,
  val imageSource: String,
  val title: String,
  val summary: String,
  val skills: List<SkillDTO>,
  val languages: List<LanguageDTO>,
  val sideProjects: List<ExperienceDTO>,
  val workHistory: List<ExperienceDTO>,
  val hobbies: List<String>,
  val education: List<EducationDTO>,
  val isReadyForPublishing: Boolean
)