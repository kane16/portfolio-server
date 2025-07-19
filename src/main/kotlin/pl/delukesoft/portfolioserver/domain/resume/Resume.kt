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
  val title: String,
  val summary: String,
  @DBRef(lazy = false)
  val skills: List<Skill>,
  val experience: List<Experience>,
  val sideProjects: List<Experience>,
  val image: Image? = null,
  @DBRef(lazy = false)
  val hobbies: List<Hobby>,
  val languages: List<WorkLanguage>,
  val createdOn: LocalDateTime,
  val lastModified: LocalDateTime
)
