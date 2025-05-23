package pl.delukesoft.portfolioserver

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
@Profile("test")
class TestcontainersConfiguration {

  @Bean
  @ServiceConnection
  fun mongoDbContainer(): MongoDBContainer {
    return MongoDBContainer(
      DockerImageName.parse("kane16/delukesoft_test_mongo_db:1.0.0").asCompatibleSubstituteFor("mongo")
    ).apply {
      withReuse(true)
    }

  }

}
