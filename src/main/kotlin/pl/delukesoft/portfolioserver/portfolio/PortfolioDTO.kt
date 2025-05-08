package pl.delukesoft.portfolioserver.portfolio

data class PortfolioDTO(
  val id: Long? = null,
  val shortDescription: String,
  val dataMatrix: List<DataMatrixDTO>
)
