package pl.delukesoft.portfolioserver.utility.loader.model

data class UploadSkill(
  val name: String,
  val level: String,
  val techDomains: List<String> = emptyList(),
  val description: String? = null
) {
}