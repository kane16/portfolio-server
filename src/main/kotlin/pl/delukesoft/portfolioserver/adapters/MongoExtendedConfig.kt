package pl.delukesoft.portfolioserver.adapters

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import pl.delukesoft.portfolioserver.domain.resume.read.model.LanguageLevel

@Configuration
class MongoExtendedConfig {

  @Bean
  fun customConversions(
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