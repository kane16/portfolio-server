package pl.delukesoft.portfolioserver.domain.resume.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Language")
data class Language(
  val id: Long? = null,
  val name: String
)
