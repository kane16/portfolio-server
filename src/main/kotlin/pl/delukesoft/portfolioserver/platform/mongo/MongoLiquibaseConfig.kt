package pl.delukesoft.portfolioserver.platform.mongo

import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.resource.ClassLoaderResourceAccessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(
  prefix = "app.liquibase",
  name = ["enabled"],
  havingValue = "true",
  matchIfMissing = true
)
class MongoLiquibaseConfig {

  @Bean(initMethod = "run")
  fun mongoLiquibaseRunner(
    @Value("\${app.liquibase.change-log}") changeLog: String,
    @Value("\${spring.mongodb.uri}") connectionString: String
  ): MongoLiquibaseRunner =
    MongoLiquibaseRunner(connectionString, changeLog)

  class MongoLiquibaseRunner(
    private val connectionString: String,
    private val changeLog: String,
  ) {

    fun run() {
      val resourceAccessor = ClassLoaderResourceAccessor(javaClass.classLoader)
      val changeLogPath = changeLog.removePrefix("classpath:")

      val database = DatabaseFactory.getInstance()
        .openDatabase(connectionString, null, null, null, resourceAccessor)
      try {
        Liquibase(changeLogPath, resourceAccessor, database).update("")
      } finally {
        database.close()
      }
    }
  }
}
