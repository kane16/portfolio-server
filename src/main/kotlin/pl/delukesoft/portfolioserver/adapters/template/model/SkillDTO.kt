package pl.delukesoft.portfolioserver.adapters.template.model

data class SkillDTO(
  val name: String,
  val description: String? = null,
  val level: Int? = null,
)
