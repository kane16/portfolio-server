package pl.delukesoft.portfolioserver

import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Import(TestcontainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("bdd")
@CucumberContextConfiguration
class PortfolioServerApplicationTests {

  private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

  @Test
  fun contextLoads() {
    log.info("Context loaded")
  }

}
