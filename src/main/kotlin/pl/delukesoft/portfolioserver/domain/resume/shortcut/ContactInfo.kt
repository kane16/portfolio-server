package pl.delukesoft.portfolioserver.domain.resume.shortcut

data class ContactInfo(
  val email: String,
  val phone: String,
  val location: String,
  val linkedin: String,
  val github: String,
  val timezone: String
)