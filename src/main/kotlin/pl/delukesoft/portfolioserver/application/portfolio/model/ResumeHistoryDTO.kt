package pl.delukesoft.portfolioserver.application.portfolio.model

data class ResumeHistoryDTO(
  val defaultPortfolio: ResumeVersionDTO? = null,
  val history: List<ResumeVersionDTO>
)
