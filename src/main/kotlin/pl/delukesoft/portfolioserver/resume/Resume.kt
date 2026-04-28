package pl.delukesoft.portfolioserver.resume

import pl.delukesoft.portfolioserver.resume.education.Education
import pl.delukesoft.portfolioserver.resume.experience.Experience
import pl.delukesoft.portfolioserver.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.resume.language.Language
import pl.delukesoft.portfolioserver.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.resume.skill.Skill
import java.time.LocalDateTime

data class Resume(
  val shortcut: ResumeShortcut,
  val skills: List<Skill> = emptyList(),
  val experience: List<Experience> = emptyList(),
  val sideProjects: List<Experience> = emptyList(),
  val education: List<Education> = emptyList(),
  val hobbies: List<Hobby> = emptyList(),
  val languages: List<Language> = emptyList(),
  val createdOn: LocalDateTime = LocalDateTime.now(),
  val lastModified: LocalDateTime = LocalDateTime.now()
)
