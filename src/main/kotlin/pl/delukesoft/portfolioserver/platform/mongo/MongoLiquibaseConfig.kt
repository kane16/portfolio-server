package pl.delukesoft.portfolioserver.platform.mongo

import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.resource.ClassLoaderResourceAccessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix = "app.liquibase", name = ["enabled"], havingValue = "true", matchIfMissing = true)
class MongoLiquibaseConfig {

  @Bean(initMethod = "run")
  fun mongoLiquibaseRunner(
    mongoProperties: MongoProperties,
    @Value("\${app.liquibase.change-log}") changeLog: String,
    @Value("\${spring.data.mongodb.username}") mongoUsername: String,
    @Value("\${spring.data.mongodb.password}") mongoPassword: String
  ): MongoLiquibaseRunner = MongoLiquibaseRunner(mongoProperties, mongoUsername, mongoPassword, changeLog)

  class MongoLiquibaseRunner(
    private val mongoProperties: MongoProperties,
    private val mongoUsername: String,
    private val mongoPassword: String,
    private val changeLog: String,
  ) {

    fun run() {
      val resourceAccessor = ClassLoaderResourceAccessor(javaClass.classLoader)
      val changeLogPath = changeLog.removePrefix("classpath:")

      val database = DatabaseFactory.getInstance()
        .openDatabase(mongoProperties.connectionString, mongoUsername, mongoPassword, null, resourceAccessor)
      try {
        Liquibase(changeLogPath, resourceAccessor, database).update("")
      } finally {
        database.close()
      }
    }
  }
}
