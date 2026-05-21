package pl.delukesoft.portfolioserver.resume.history

data class ResumeHistoryDTO(
  val defaultPortfolio: ResumeVersionDTO? = null,
  val history: List<ResumeVersionDTO>
)
