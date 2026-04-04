package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.domain.resume.Resume

@Document(collection = "ResumeVersion")
data class ResumeVersion(
  val id: Long? = null,
  val resume: Resume,
  val version: Long,
  val state: ResumeVersionState
)
