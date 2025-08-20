package pl.delukesoft.portfolioserver.domain.resume.timespan

import java.time.LocalDate

data class Timeframe(
  val start: LocalDate,
  val end: LocalDate? = LocalDate.now()
) {

  fun isOverlappingWith(next: Timeframe): Boolean {
    return end != null && next.start > end
  }

}