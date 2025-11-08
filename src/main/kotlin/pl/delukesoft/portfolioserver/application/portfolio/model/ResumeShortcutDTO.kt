package pl.delukesoft.portfolioserver.application.portfolio.model

import pl.delukesoft.portfolioserver.adapters.image.Image

data class ResumeShortcutDTO(
  val title: String,
  val summary: String,
  val image: Image,
)