package pl.delukesoft.portfolioserver

import com.fasterxml.jackson.databind.json.JsonMapper
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequestService
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.application.template.model.PrintDTO
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.print.DocumentGenerationService

@TestConfiguration(proxyBeanMethods = false)
@Profile("test")
class TestcontainersConfiguration {
  private var jsonMapper = JsonMapper()

  @Bean
  @ServiceConnection
  fun mongoDbContainer(): MongoDBContainer {
    return MongoDBContainer(
      DockerImageName.parse("kane16/delukesoft_dev_mongo_db:1.0.0").asCompatibleSubstituteFor("mongo")
    ).apply {
      withReuse(true)
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
    return service
  }

}
