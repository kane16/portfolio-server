package pl.delukesoft.portfolioserver.application.resume.experience.timeframe

import java.time.LocalDate

data class TimeframeDTO(
  val start: LocalDate,
  val end: LocalDate? = null,
)
