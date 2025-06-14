package pl.delukesoft.portfolioserver

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import pl.delukesoft.portfolioserver.portfolio.model.LanguageLevel

@Configuration
class MongoExtendedConfig(): AbstractMongoClientConfiguration() {

  @Value("\${spring.data.mongodb.database}")
  private lateinit var databaseName: String

  @Value("\${spring.data.mongodb.uri}")
  private lateinit var mongoUri: String

  override fun getDatabaseName(): String {
    return MongoClients.create(mongoUri)
          .getDatabase(databaseName)
          .name
  }

  override fun mongoClient(): MongoClient {
    return MongoClients.create(mongoUri)
  }

  override fun customConversions(
  ): MongoCustomConversions {
    return MongoCustomConversions(
      listOf(
        LanguageLevelReadingConverter(),
        LanguageLevelWritingConverter()
      )
    )
  }

  @ReadingConverter
  class LanguageLevelReadingConverter : Converter<Int, LanguageLevel> {
    override fun convert(source: Int): LanguageLevel? {
      return LanguageLevel.values().find { it.level == source }
    }
  }

  @WritingConverter
  class LanguageLevelWritingConverter : Converter<LanguageLevel, Int> {
    override fun convert(source: LanguageLevel): Int {
      return source.level
    }
  }

}