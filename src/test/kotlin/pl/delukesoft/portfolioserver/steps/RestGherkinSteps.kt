package pl.delukesoft.portfolioserver.steps

import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions.assertEquals
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException


class RestGherkinSteps(
  private val baseRestClient: BaseRestClient
) : En {

  var result = ResponseEntity.ok("OK")

  init {
    defineSteps()
  }

  fun defineSteps() {
    Given("User sign in with credentials: ", baseRestClient::attachTokenWithLoginRequest)
    When("{string} request is sent to endpoint {string} with no body") { method: String, endpoint: String ->
      try {
        result = sendRequest(method, endpoint, "")
      } catch (e: HttpClientErrorException) {
        result = ResponseEntity.status(e.statusCode)
          .body(e.responseBodyAsString)
      }
    }

    When("{string} request is sent to endpoint {string} with body:") { method: String, endpoint: String, body: String ->
      try {
        result = sendRequest(method, endpoint, body)
      } catch (e: HttpClientErrorException) {
        result = ResponseEntity.status(e.statusCode)
          .body(e.responseBodyAsString)
      }
    }

    Then("Response status code should be {int}") { statusCode: Int ->
      assertEquals(result.statusCode.value(), statusCode)
    }

    Then("Response body should be:") { responseBody: String ->
      if (!responseBody.contains("{")) {
        assertEquals(responseBody, result.body)
      } else {
        JSONAssert.assertEquals(responseBody, result.body, true)
      }
    }
  }

  private fun sendRequest(method: String, endpoint: String, body: String): ResponseEntity<String> {
      return when (method) {
          "GET" -> baseRestClient.get(endpoint, String::class.java)
          "POST" -> baseRestClient.post(endpoint, body, String::class.java)
          "PUT" -> baseRestClient.put(endpoint, body, String::class.java)
          "DELETE" -> baseRestClient.delete(endpoint, String::class.java)
          else -> throw IllegalArgumentException("Invalid method: $method")
      }
  }


}