package pl.delukesoft.portfolioserver.utility.loader

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.delukesoft.portfolioserver.utility.loader.model.UploadResume

@RestController
@RequestMapping("/data/upload")
@Profile("dev","test")
class DataLoaderController(
  private val dataLoaderFacade: DataLoaderFacade
) {

  @PostMapping("/read/resume")
  fun uploadReadResume(
    @RequestBody uploadResumes: List<UploadResume>
  ): Boolean {
    return dataLoaderFacade.loadDataFromReadResumes(uploadResumes)
  }

}