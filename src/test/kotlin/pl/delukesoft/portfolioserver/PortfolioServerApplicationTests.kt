package pl.delukesoft.portfolioserver

import io.cucumber.core.options.Constants
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Test
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.SelectClasspathResource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles

@Import(TestcontainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@SelectClasspathResource("features/pdf/admin-template-generation.feature")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "pl.delukesoft.portfolioserver.steps")
@CucumberContextConfiguration
class PortfolioServerApplicationTests {

  @Test
  fun contextLoads() {
  }

}
