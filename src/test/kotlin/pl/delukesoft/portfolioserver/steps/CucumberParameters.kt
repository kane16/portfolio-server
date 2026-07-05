package pl.delukesoft.portfolioserver.steps

import io.cucumber.java.ParameterType

class CucumberParameters {

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