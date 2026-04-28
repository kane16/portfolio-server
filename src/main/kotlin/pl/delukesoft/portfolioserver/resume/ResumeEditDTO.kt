package pl.delukesoft.portfolioserver.resume

import pl.delukesoft.portfolioserver.resume.language.LanguageDTO
import pl.delukesoft.portfolioserver.resume.education.EducationDTO
import pl.delukesoft.portfolioserver.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.resume.skill.SkillDTO
import pl.delukesoft.portfolioserver.resume.shortcut.contact.ContactInfo

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
  val contact: ContactInfo? = null
)