package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.adapters.auth.User

@Document(collection = "ResumeHistory")
data class ResumeHistory(
  val id: Long? = null,
  val defaultResume: ResumeVersion? = null,
  @DBRef(lazy = true) val versions: List<ResumeVersion> = emptyList(),
  val user: User
)
