package pl.delukesoft.portfolioserver.steps

import com.jayway.jsonpath.internal.JsonFormatter.prettyPrint
import io.cucumber.java.Before
import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions.assertEquals
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompare
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import pl.delukesoft.portfolioserver.domain.resume.ResumeRepository
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryRepository


class RestGherkinSteps(
  private val baseRestClient: BaseRestClient,
  private val resumeRepository: ResumeRepository,
  private val resumeHistoryRepository: ResumeHistoryRepository
) : En {

  var result = ResponseEntity.ok("OK")
  private var logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

  init {
    defineSteps()
  }

  @Before
  fun resetToken() {
    baseRestClient.resetToken()
  }

  fun defineSteps() {
    val dbResumes = resumeRepository.findAll()
    val dbHistoryResumes = resumeHistoryRepository.findAll()
    Given("User is authorized with token: {string}", baseRestClient::attachTokenToRequest)
    Given("All resumes are deleted from database") {
      resumeHistoryRepository.deleteAll()
      resumeRepository.deleteAll()
    }
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
      assertEquals(statusCode, result.statusCode.value())
    }

    Then("Response body should be:") { responseBody: String ->
      if (!responseBody.contains("{")) {
        assertEquals(responseBody, result.body)
      } else {
        if (JSONCompare.compareJSON(result.body.toString(), responseBody, JSONCompareMode.LENIENT).failed() ) {
          logger.error("Invalid response body:\n{}", prettyPrint(result.body))
          logger.error("Response should be JSON:\n{}", prettyPrint(responseBody))
          logger.error(
            "Differences:\n{}",
            JSONCompare.compareJSON(result.body.toString(), responseBody, JSONCompareMode.LENIENT).message
          )
        }
        JSONAssert.assertEquals(responseBody, result.body, true)
      }
    }
    Then("Restore DB resumes") {
      resumeRepository.saveAll(dbResumes)
      resumeHistoryRepository.saveAll(dbHistoryResumes)
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