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
import pl.delukesoft.portfolioserver.auth.AuthRequestService

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
  fun authRequestService(): AuthRequestService {
    val service = mockk<AuthRequestService>()
    every { service.getUserAuthorities("user") } returns listOf("ROLE_USER")
    every { service.getUserAuthorities("admin") } returns listOf("ROLE_USER", "ROLE_ADMIN")
    return service
  }

}
