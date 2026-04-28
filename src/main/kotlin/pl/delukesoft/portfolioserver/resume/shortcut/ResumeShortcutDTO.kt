package pl.delukesoft.portfolioserver.resume.shortcut

import pl.delukesoft.portfolioserver.media.Image
import pl.delukesoft.portfolioserver.resume.shortcut.contact.ContactInfo

data class ResumeShortcutDTO(
  val title: String,
  val summary: String,
  val image: Image,
  val contact: ContactInfo? = null
)