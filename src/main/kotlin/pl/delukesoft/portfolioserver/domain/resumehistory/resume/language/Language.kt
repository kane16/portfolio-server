package pl.delukesoft.portfolioserver.domain.resumehistory.resume.language

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Language")
data class Language(
  val id: Long? = null,
  val name: String
)
