package pl.delukesoft.portfolioserver.data

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.read.ResumeService

@Component
@Profile("test")
class TestDataLoader(
  private val resumeService: ResumeService
): ApplicationRunner {

  override fun run(args: ApplicationArguments?) {
    println()
  }

}