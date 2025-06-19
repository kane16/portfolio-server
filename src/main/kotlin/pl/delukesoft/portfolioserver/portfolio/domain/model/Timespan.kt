package pl.delukesoft.portfolioserver.portfolio.domain.model

import java.time.LocalDate

data class Timespan(
  val start: LocalDate,
  val end: LocalDate? = LocalDate.now()
)
