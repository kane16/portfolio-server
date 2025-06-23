package pl.delukesoft.portfolioserver.domain.resume.read.model

import java.time.LocalDate

data class Timespan(
  val start: LocalDate,
  val end: LocalDate? = LocalDate.now()
)
