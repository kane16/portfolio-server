package pl.delukesoft.portfolioserver.domain.resume.language

import org.springframework.data.mongodb.core.mapping.DBRef

data class WorkLanguage(
  @DBRef(lazy = false) val language: Language,
  val level: LanguageLevel,
) {
}