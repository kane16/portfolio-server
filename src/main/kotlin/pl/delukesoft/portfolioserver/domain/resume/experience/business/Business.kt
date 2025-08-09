package pl.delukesoft.portfolioserver.domain.resume.experience.business

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Business")
data class Business(
  @Id val id: Long? = null,
  val name: String,
  val username: String
)