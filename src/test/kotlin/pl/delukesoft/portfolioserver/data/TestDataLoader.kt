package pl.delukesoft.portfolioserver.data

import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import pl.delukesoft.blog.image.Image
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.model.Business
import pl.delukesoft.portfolioserver.domain.resume.model.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.model.WorkLanguage
import pl.delukesoft.portfolioserver.domain.resume.read.ResumeService

@Component
@Profile("test")
class TestDataLoader(
  private val resumeService: ResumeService,
): ApplicationRunner {

  override fun run(args: ApplicationArguments?) {
    println()
  }

}