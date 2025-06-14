package pl.delukesoft.portfolioserver

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

  override fun getDatabaseName(): String {
    return databaseName
  }

  override fun customConversions(): MongoCustomConversions {
    return MongoCustomConversions(
      listOf(
        LanguageLevelConverter(),
        LanguageLevelWritingConverter()
      )
    )
  }

  @ReadingConverter
  class LanguageLevelConverter: Converter<Int, LanguageLevel> {
    override fun convert(source: Int): LanguageLevel? {
      return LanguageLevel.values().find { it.level == source }
    }
  }

  @WritingConverter
  class LanguageLevelWritingConverter: Converter<LanguageLevel, Int> {
    override fun convert(source: LanguageLevel): Int {
      return source.level
    }
  }

}