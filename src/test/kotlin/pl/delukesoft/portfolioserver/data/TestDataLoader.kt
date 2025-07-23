package pl.delukesoft.portfolioserver.data

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService
import pl.delukesoft.portfolioserver.utility.loader.DataLoaderController
import pl.delukesoft.portfolioserver.utility.loader.model.UploadResume
import java.nio.file.Files

@Component
@Profile("bdd")
class TestDataLoader(
  private val resourceLoader: ResourceLoader,
  private val objectMapper: ObjectMapper,
  private val dataLoaderController: DataLoaderController,
  private val resumeHistoryService: ResumeHistoryService
) : ApplicationRunner {

  private val log = LoggerFactory.getLogger(TestDataLoader::class.java)

  override fun run(args: ApplicationArguments?) {
    if (!resumeHistoryService.isInitialized()) {
      val data = Files.readString(resourceLoader.getResource("classpath:data/read_resume_test_data.json").file.toPath())
      val resumes: List<UploadResume> = objectMapper.readValue(data)
      val result = dataLoaderController.uploadReadResume(resumes)
      if (!result) {
        log.error("Test data upload failed")
      } else {
        log.info("Test data uploaded")
      }
    } else {
      log.info("Test data already uploaded")
    }
  }

}