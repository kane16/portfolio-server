package pl.delukesoft.portfolioserver.application.resume.experience.timeframe

import java.time.LocalDate

data class TimeframeDTO(
  val from: LocalDate,
  val to: LocalDate? = null,
)
