package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.Resume

@Document(collection = "ResumeHistory")
data class ResumeHistory(
  val id: Long? = null,
  @DBRef(lazy = false) val defaultResume: ResumeVersion,
  @DBRef(lazy = true) val versions: List<ResumeVersion> = emptyList(),
  val user: User
)
