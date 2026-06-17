package pl.delukesoft.portfolioserver

import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import pl.delukesoft.portfolioserver.mongo.MongoContainer

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

  companion object {

    @JvmStatic
    val mongoDBContainer: MongoContainer = MongoContainer().apply { start() }

    @DynamicPropertySource
    @JvmStatic
    fun setMongoUri(registry: DynamicPropertyRegistry) {
      mongoDBContainer.registerProperties(registry)
    }

  }

}