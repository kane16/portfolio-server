package pl.delukesoft.portfolioserver.steps

import com.jayway.jsonpath.internal.JsonFormatter.prettyPrint
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions.assertEquals
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompare
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import pl.delukesoft.portfolioserver.domain.resume.ResumeRepository
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessRepository
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyRepository
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageRepository
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillRepository
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainRepository
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryRepository
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersionRepository
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorRepository


class BehaviourTestsSteps(
  private val baseRestClient: BaseRestClient,
  private val resumeRepository: ResumeRepository,
  private val resumeHistoryRepository: ResumeHistoryRepository,
  private val resumeVersionRepository: ResumeVersionRepository,
  private val generatorRepository: GeneratorRepository,
  private val skillRepository: SkillRepository,
  private val businessRepository: BusinessRepository,
  private val skillDomainRepository: SkillDomainRepository,
  private val hobbyRepository: HobbyRepository,
  private val languageRepository: LanguageRepository
) : En {

  var result = ResponseEntity.ok("OK")
  private var logger = org.slf4j.LoggerFactory.getLogger(this::class.java)
  val intialDbResumes = resumeRepository.findAll()
  val initialDbResumeVersions = resumeVersionRepository.findAll()
  val initialDbHistoryResumes = resumeHistoryRepository.findAll()
  val initialSequences = generatorRepository.findAll()
  val initialSkills = skillRepository.findAll()
  val initialBusiness = businessRepository.findAll()
  val initialSkillDomains = skillDomainRepository.findAll()
  val initialHobbies = hobbyRepository.findAll()
  val initialLanguages = languageRepository.findAll()

  init {
    defineSteps()
  }

  @Before
  fun beforeScenario() {
    baseRestClient.resetToken()
  }

  @After
  fun afterScenario() {
    resumeHistoryRepository.deleteAll()
    resumeVersionRepository.deleteAll()
    resumeRepository.deleteAll()
    generatorRepository.deleteAll()
    skillRepository.deleteAll()
    skillDomainRepository.deleteAll()
    businessRepository.deleteAll()
    hobbyRepository.deleteAll()
    languageRepository.deleteAll()
    languageRepository.saveAll(initialLanguages)
    businessRepository.saveAll(initialBusiness)
    hobbyRepository.saveAll(initialHobbies)
    skillDomainRepository.saveAll(initialSkillDomains)
    skillRepository.saveAll(initialSkills)
    resumeRepository.saveAll(intialDbResumes)
    resumeVersionRepository.saveAll(initialDbResumeVersions)
    resumeHistoryRepository.saveAll(initialDbHistoryResumes)
    generatorRepository.saveAll(initialSequences)
  }

  fun defineSteps() {
    Given("User is authorized with token: {string}", baseRestClient::attachTokenToRequest)
    Given("All resumes are deleted from database") {
      resumeHistoryRepository.deleteAll()
      resumeVersionRepository.deleteAll()
      resumeRepository.deleteAll()
      generatorRepository.deleteAll()
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
        if (JSONCompare.compareJSON(result.body.toString(), responseBody, JSONCompareMode.LENIENT).failed()) {
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