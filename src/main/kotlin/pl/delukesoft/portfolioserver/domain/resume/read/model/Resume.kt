package pl.delukesoft.portfolioserver.domain.resume.read.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.blog.image.Image
import pl.delukesoft.portfolioserver.adapters.auth.User
import java.time.LocalDateTime

@Document(collection = "CV")
data class Resume(
  @Id val id: Long? = null,
  val user: User,
  val title: String,
  val summary: String,
  val skills: List<Skill>,
  val experience: List<Experience>,
  val sideProjects: List<Experience>,
  val image: Image? = null,
  val hobbies: List<String>,
  val languages: List<WorkLanguage>,
  val visible: Boolean,
  val createdOn: LocalDateTime,
  val lastModified: LocalDateTime
)
