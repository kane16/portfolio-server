package pl.delukesoft.portfolioserver.application.portfolio.model

import pl.delukesoft.portfolioserver.adapters.image.Image
import pl.delukesoft.portfolioserver.domain.resume.shortcut.contact.ContactInfo

data class ResumeShortcutDTO(
  val title: String,
  val summary: String,
  val image: Image,
  val contact: ContactInfo? = null
)