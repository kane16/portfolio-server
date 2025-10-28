package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.domain.resume.education.Education
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import java.time.LocalDateTime

@Document(collection = "Resume")
data class Resume(
  @Id val id: Long? = null,
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
