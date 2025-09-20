package pl.delukesoft.portfolioserver.steps

import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.java.ParameterType

class CucumberParameters {

  private val objectMapper = ObjectMapper()

  @ParameterType("should|should not")
  fun should(value: String): Truthy {
    return Truthy(value == "should")
  }

  @ParameterType("is|is no")
  fun `is`(value: String): Truthy {
    return Truthy(value == "is")
  }

  @ParameterType("true|false")
  fun bool(value: String): Truthy {
    return Truthy(value == "true")
  }


}