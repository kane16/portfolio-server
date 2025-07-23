package pl.delukesoft.portfolioserver.domain.resume

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import pl.delukesoft.portfolioserver.TestcontainersConfiguration

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration::class)
class ResumeIntegrationTest {

  @Autowired
  private lateinit var resumeService: ResumeService

  @Test
  fun shouldAdd() {
    val resume = resumeService.getDefaultCV(null)
    println()
  }

}