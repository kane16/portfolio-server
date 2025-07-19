package pl.delukesoft.portfolioserver.domain.resume.hobby

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Hobby")
data class Hobby(
  @Id
  val id: Long? = null,
  val name: String
) {
}