package pl.delukesoft.portfolioserver.resume.history

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.security.User

@Document(collection = "ResumeHistory")
data class ResumeHistory(
  val id: Long? = null,
  @DBRef(lazy = false) val defaultResume: ResumeVersion? = null,
  @DBRef(lazy = true) val versions: List<ResumeVersion> = emptyList(),
  val user: User
)
