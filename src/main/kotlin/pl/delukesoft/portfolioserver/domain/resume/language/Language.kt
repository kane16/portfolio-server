package pl.delukesoft.portfolioserver.domain.resume.language

data class Language(
  val id: Long? = null,
  val name: String,
  val level: LanguageLevel,
)
