package pl.delukesoft.portfolioserver.portfolio.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.blog.image.Image
import java.time.LocalDateTime

@Document(collection = "CV")
data class Curriculum(
  @Id val id: Long? = null,
  val shortDescription: String,
  val skills: List<Skill>,
  val experience: List<Experience>,
  val image: Image? = null,
  val createdOn: LocalDateTime,
  val lastModified: LocalDateTime
)
