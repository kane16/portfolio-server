package pl.delukesoft.portfolioserver.domain.resumehistory.resume

import java.time.LocalDate

data class Timespan(
  val start: LocalDate,
  val end: LocalDate? = LocalDate.now()
)
