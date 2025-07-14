package pl.delukesoft.portfolioserver.data

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
import pl.delukesoft.portfolioserver.utility.loader.DataLoaderController
import pl.delukesoft.portfolioserver.utility.loader.model.UploadResume
import java.nio.file.Files

@Component
@Profile("test")
class TestDataLoader(
  private val resourceLoader: ResourceLoader,
  private val objectMapper: ObjectMapper,
  private val dataLoaderController: DataLoaderController
): ApplicationRunner {

  override fun run(args: ApplicationArguments?) {
    val data = Files.readString(resourceLoader.getResource("classpath:data/read_resume_test_data.json").file.toPath())
    val resumes: List<UploadResume> = objectMapper.readValue(data)
    val result = dataLoaderController.uploadReadResume(resumes)
    println("Test data loaded: $result")
    println("Test data uploaded")
  }

}