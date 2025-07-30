package pl.delukesoft.portfolioserver.domain.resume

import pl.delukesoft.portfolioserver.adapters.image.Image
import pl.delukesoft.portfolioserver.adapters.auth.User

data class ResumeShortcut(
  val user: User,
  val title: String,
  val summary: String,
  val image: Image? = null,
)
