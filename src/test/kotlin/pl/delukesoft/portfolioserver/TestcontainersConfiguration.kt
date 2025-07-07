package pl.delukesoft.portfolioserver

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
import pl.delukesoft.portfolioserver.domain.generation.DocumentGenerationService

@TestConfiguration(proxyBeanMethods = false)
@Profile("test")
class TestcontainersConfiguration {

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
        match { print -> print.getResumeId() == 1L },
        any()
      )
    } returns "html 1"
    every {
      service.generateResumeHtml(
        match { print -> print.getResumeId() == 2L },
        any()
      )
    } returns "html 2"
    every {
      service.generateResumeHtml(
        match { print -> print.getResumeId() == 3L },
        any()
      )
    } returns "html 3"
    return service
  }

  @Bean
  @Primary
  fun authRequestService(): AuthRequestService {
    val service = mockk<AuthRequestService>()
    every { service.getUser("Bearer admin") } returns User(1L, "admin", "", listOf("ROLE_USER", "ROLE_ADMIN"))
    every { service.getUser("Bearer user") } returns User(2L, "user", "", listOf("ROLE_USER"))
    every { service.getUser("Bearer candidate") } returns User(3L, "candidate", "", listOf("ROLE_USER", "ROLE_CANDIDATE"))
    return service
  }

}
