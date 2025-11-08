package pl.delukesoft.portfolioserver

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequestService
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.adapters.print.DocumentGenerationService
import pl.delukesoft.portfolioserver.application.pdf.model.PrintDTO
import java.time.Duration


@TestConfiguration(proxyBeanMethods = false)
@Profile("test", "bdd")
class TestcontainersConfiguration {
  private var jsonMapper = JsonMapper.builder()
    .addModule(JavaTimeModule())
    .addModule(KotlinModule.Builder().build())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .build()

  @Bean
  @ServiceConnection
  fun mongoDbContainer(): MongoDBContainer {
    return MongoDBContainer(
      DockerImageName.parse("kane16/delukesoft_test_mongo_db:1.0.0").asCompatibleSubstituteFor("mongo")
    )
      .waitingFor(
        Wait.forLogMessage(".*Waiting for connections.*", 1)
          .withStartupTimeout(Duration.ofMinutes(2))
      )
      .apply {
        withReuse(false)
      }

  }

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
  @Primary
  fun authRequestService(): AuthRequestService {
    val service = mockk<AuthRequestService>()
    every { service.getUser("Bearer admin") } returns User("admin", "", listOf("ROLE_USER", "ROLE_ADMIN"))
    every { service.getUser("Bearer user") } returns User("user", "", listOf("ROLE_USER"))
    every { service.getUser("Bearer candidate") } returns User("candidate", "", listOf("ROLE_USER", "ROLE_CANDIDATE"))
    every { service.getUser("Bearer candidate_empty") } returns User("candidate_empty", "", listOf("ROLE_CANDIDATE"))
    return service
  }

}
