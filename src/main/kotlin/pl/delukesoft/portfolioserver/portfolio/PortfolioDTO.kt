package pl.delukesoft.portfolioserver.portfolio

data class PortfolioDTO(
  val id: Long? = null,
  val title: String,
  val description: String,
  val skills: DataMatrixDTO,
  val experience: DataMatrixDTO,
  val business: DataMatrixDTO,
  val languages: DataMatrixDTO,
)
