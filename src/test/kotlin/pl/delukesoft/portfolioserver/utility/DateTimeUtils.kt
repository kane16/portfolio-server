package pl.delukesoft.portfolioserver.utility

import org.assertj.core.api.Assertions.assertThat
import java.time.Duration
import java.time.LocalDateTime

object DateTimeUtils {

  fun assertEqualsDateTimes(firstDate: LocalDateTime, secondDate: LocalDateTime) {
    val tolerance = Duration.ofMillis(1)
    val timeDifference = Duration.between(firstDate, secondDate).abs()
    assertThat(timeDifference).isLessThanOrEqualTo(tolerance)
  }

  fun assertNotEqualsDateTimes(firstDate: LocalDateTime, secondDate: LocalDateTime) {
    val tolerance = Duration.ofMillis(1)
    val timeDifference = Duration.between(firstDate, secondDate).abs()
    assertThat(timeDifference).isGreaterThanOrEqualTo(tolerance)
  }

}