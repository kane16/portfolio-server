package pl.delukesoft.portfolioserver.pdf.model

data class SkillDTO(
  val name: String,
  val description: String? = null,
  val level: Int? = null,
)
