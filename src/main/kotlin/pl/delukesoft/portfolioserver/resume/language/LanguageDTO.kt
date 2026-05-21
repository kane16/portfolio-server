package pl.delukesoft.portfolioserver.resume.language

data class LanguageDTO(
  val name: String,
  val level: String,
  val id: Long? = null,
) {
}