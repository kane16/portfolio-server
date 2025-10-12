package pl.delukesoft.portfolioserver.application.portfolio.model

data class LanguageDTO(
  val name: String,
  val level: String,
  val id: Long? = null,
) {
}