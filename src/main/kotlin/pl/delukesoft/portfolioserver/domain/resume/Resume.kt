package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.blog.image.Image
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.WorkLanguage
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import java.time.LocalDateTime

@Document(collection = "Resume")
data class Resume(
  @Id val id: Long? = null,
  val shortcut: ResumeShortcut,
  val skills: List<Skill> = emptyList(),
  val experience: List<Experience> = emptyList(),
  val sideProjects: List<Experience> = emptyList(),
  val hobbies: List<Hobby> = emptyList(),
  val languages: List<WorkLanguage> = emptyList(),
  val createdOn: LocalDateTime = LocalDateTime.now(),
  val lastModified: LocalDateTime = LocalDateTime.now()
) {

}
