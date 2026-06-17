package pl.delukesoft.portfolioserver.mongo

import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.mongodb.MongoDBContainer
import org.testcontainers.utility.DockerImageName
import java.time.Duration

class MongoContainer :
  MongoDBContainer(DockerImageName.parse("mongo:7.0")) {

  init {
    waitingFor(
      Wait.forLogMessage(".*Waiting for connections.*", 1)
        .withStartupTimeout(Duration.ofMinutes(2))
    )
    withReuse(false)
  }

  fun registerProperties(registry: DynamicPropertyRegistry) {
    registry.add("spring.mongodb.uri") { getReplicaSetUrl("portfolio") }
  }

}