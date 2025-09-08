package pl.delukesoft.portfolioserver.domain.resume.shortcut

import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.adapters.image.Image

data class ResumeShortcut(
  val user: User,
  val title: String,
  val summary: String,
  val image: Image? = null,
)