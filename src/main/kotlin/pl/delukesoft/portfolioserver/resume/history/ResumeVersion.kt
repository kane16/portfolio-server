package pl.delukesoft.portfolioserver.resume.history

import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.resume.Resume

@Document(collection = "ResumeVersion")
data class ResumeVersion(
  val id: Long? = null,
  val resume: Resume,
  val version: Long,
  val state: ResumeVersionState
)
