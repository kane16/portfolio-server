package pl.delukesoft.portfolioserver.resume.history

data class ResumeVersionDTO(
  val id: Long,
  val title: String,
  val summary: String,
  val version: Long,
  val state: String
)