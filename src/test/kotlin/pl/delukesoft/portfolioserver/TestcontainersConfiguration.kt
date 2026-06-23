package pl.delukesoft.portfolioserver

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.databind.JsonNode
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import pl.delukesoft.portfolioserver.document.DocumentGenerationService
import pl.delukesoft.portfolioserver.document.PrintDTO
import pl.delukesoft.authplugin.author.Author
import pl.delukesoft.authplugin.common.AuthClient
import pl.delukesoft.authplugin.config.external.ExternalServiceProperties
import pl.delukesoft.authplugin.config.external.ExternalServicesConfiguration
import pl.delukesoft.authplugin.common.ErrorBody
import pl.delukesoft.authplugin.common.ErrorResponse
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.authplugin.security.JwtService
import pl.delukesoft.authplugin.security.User
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthorAdditionalInfo
import pl.delukesoft.portfolioserver.resume.skill.domain.SkillDomain


@TestConfiguration(proxyBeanMethods = false)
@Profile("test", "bdd")
class TestcontainersConfiguration {

  private var jsonMapper = JsonMapper.builder()
    .addModule(JavaTimeModule())
    .addModule(KotlinModule.Builder().build())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .build()

  @Bean
  @Primary
  fun documentGenerationService(): DocumentGenerationService {
    val service = mockk<DocumentGenerationService>()
    every {
      service.generateResumeHtml(
        any(),
        any()
      )
    } answers { jsonMapper.writeValueAsString(firstArg<PrintDTO>()) }
    return service
  }

  @Bean
  fun mockAuthPlugin(authContext: AuthContext): MockAuthPlugin {
    return MockAuthPlugin(jsonMapper, authContext)
  }

  @Bean
  @Primary
  fun jwtService(mockAuthPlugin: MockAuthPlugin): JwtService {
    val service = mockk<JwtService>()
    every { service.getUser(any()) } answers { mockAuthPlugin.userFor(firstArg()) }
    return service
  }

  @Bean
  @Primary
  fun authClient(mockAuthPlugin: MockAuthPlugin): AuthClient {
    return mockAuthPlugin.authClient()
  }

}

class MockAuthPlugin(
  private val jsonMapper: JsonMapper,
  private val authContext: AuthContext
) {
  private val users = mapOf(
    "Bearer admin" to User(
      1L,
      "admin",
      arrayOf("ROLE_USER", "ROLE_AUTHOR", "ROLE_ADMIN"),
      "john.doe@example.com",
      "John",
      "Doe"
    ),
    "Bearer user" to User(
      2L,
      "user",
      arrayOf("ROLE_USER"),
      "jane.smith@example.com",
      "Jane",
      "Smith"
    ),
    "Bearer candidate" to User(
      3L,
      "candidate",
      arrayOf("ROLE_USER", "ROLE_AUTHOR"),
      "alex.tech@example.com",
      "Alex",
      "Tech"
    ),
    "Bearer candidate_empty" to User(
      100L,
      "candidate_empty",
      arrayOf("ROLE_AUTHOR"),
      "candidate_empty@example.com",
      "Łukasz",
      "Gumiński"
    )
  )

  private val initialAuthors = mapOf(
    "admin" to author(
      1L,
      1L,
      "John",
      "Doe",
      "admin",
      PortfolioAuthorAdditionalInfo(
        roles = listOf("ROLE_USER", "ROLE_AUTHOR", "ROLE_ADMIN"),
        domains = listOf(
          SkillDomain("Backend"),
          SkillDomain("Frontend"),
          SkillDomain("Framework"),
          SkillDomain("Database")
        )
      )
    ),
    "candidate" to author(
      3L,
      3L,
      "Alex",
      "Tech",
      "candidate",
      PortfolioAuthorAdditionalInfo(
        roles = listOf("ROLE_USER", "ROLE_AUTHOR"),
        domains = listOf(
          SkillDomain("JVM"),
          SkillDomain("Backend"),
          SkillDomain("Framework"),
          SkillDomain("Database")
        )
      )
    ),
    "candidate_empty" to author(
      100L,
      100L,
      "Łukasz",
      "Gumiński",
      "candidate_empty",
      PortfolioAuthorAdditionalInfo(roles = listOf("ROLE_AUTHOR"))
    )
  )

  private val authors = mutableMapOf<String, Author>()

  init {
    reset()
  }

  fun reset() {
    authors.clear()
    initialAuthors.forEach { (username, author) ->
      authors[username] = copyAuthor(author)
    }
  }

  fun userFor(token: String): User {
    return users[token] ?: throw ErrorResponse(ErrorBody("Invalid JWT Token", 401))
  }

  fun authClient(): AuthClient {
    return object : AuthClient(
      ExternalServicesConfiguration(
        ExternalServiceProperties(
          ExternalServiceProperties.ServiceConnection("auth", null),
          ExternalServiceProperties.ServiceConnection("backOffice", null)
        )
      )
    ) {
      override fun <R> get(endpoint: String, responseType: Class<R>): R {
        val response = when (endpoint) {
          "/authors/context?app=portfolio" -> currentAuthor()
          "/authors?app=portfolio", "/authors" -> authors.values.toTypedArray()
          else -> throw ErrorResponse(ErrorBody("Auth endpoint not mocked: $endpoint", 500))
        }
        return responseType.cast(response)
      }

      override fun <T, R> patch(endpoint: String, body: T, responseType: Class<R>): R {
        if (endpoint != "/authors/portfolio") {
          throw ErrorResponse(ErrorBody("Auth endpoint not mocked: $endpoint", 500))
        }
        val editAuthor = body as? Author
          ?: throw ErrorResponse(ErrorBody("Unexpected auth request body", 500))
        val username = editAuthor.username() ?: currentUsername()
        val existingAuthor = authors[username] ?: throw ErrorResponse(ErrorBody("Author not found", 404))
        val editedAuthor = Author(
          existingAuthor.id(),
          editAuthor.userId() ?: existingAuthor.userId(),
          editAuthor.firstname() ?: existingAuthor.firstname(),
          editAuthor.lastname() ?: existingAuthor.lastname(),
          username,
          editAuthor.additionalInfo() ?: existingAuthor.additionalInfo()
        )
        authors[username] = editedAuthor
        return responseType.cast(copyAuthor(editedAuthor))
      }
    }
  }

  private fun author(
    id: Long,
    userId: Long,
    firstname: String,
    lastname: String,
    username: String,
    additionalInfo: PortfolioAuthorAdditionalInfo
  ): Author {
    return Author(id, userId, firstname, lastname, username, jsonMapper.valueToTree(additionalInfo))
  }

  private fun currentAuthor(): Author {
    val username = currentUsername()
    return authors[username]?.let(::copyAuthor) ?: throw ErrorResponse(ErrorBody("Author not found", 404))
  }

  private fun currentUsername(): String {
    val token = authContext.token ?: throw ErrorResponse(ErrorBody("Anonymous access is restricted to this endpoint", 401))
    return token.removePrefix("Bearer ")
  }

  private fun copyAuthor(author: Author): Author {
    return Author(
      author.id(),
      author.userId(),
      author.firstname(),
      author.lastname(),
      author.username(),
      author.additionalInfo().deepCopy<JsonNode>()
    )
  }

}
