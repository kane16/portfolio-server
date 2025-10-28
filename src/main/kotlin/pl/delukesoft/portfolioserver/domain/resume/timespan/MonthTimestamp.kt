package pl.delukesoft.portfolioserver.domain.resume.timespan

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

data class MonthTimestamp(
  val month: Int,
  val year: Int,
) {

  fun toStartLocalDate(): LocalDate = LocalDate.of(year, month, 1)
  fun toEndLocalDate(): LocalDate = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth())

}
