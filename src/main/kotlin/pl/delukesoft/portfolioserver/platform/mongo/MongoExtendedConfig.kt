package pl.delukesoft.portfolioserver.platform.mongo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import pl.delukesoft.portfolioserver.resume.language.LanguageLevel
import pl.delukesoft.portfolioserver.resume.history.ResumeVersionState

@Configuration
class MongoExtendedConfig {

  @Bean
  fun customConversions(
  ): MongoCustomConversions {
    return MongoCustomConversions(
      listOf(
        LanguageLevelReadingConverter(),
        LanguageLevelWritingConverter(),
        ResumeVersionStateReadingConverter(),
        ResumeVersionStateWritingConverter(),
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

  @ReadingConverter
  class ResumeVersionStateReadingConverter : Converter<String, ResumeVersionState> {
    override fun convert(source: String): ResumeVersionState? {
      return ResumeVersionState.values().find { it.name == source }
    }
  }

  @WritingConverter
  class ResumeVersionStateWritingConverter : Converter<ResumeVersionState, String> {
    override fun convert(source: ResumeVersionState): String {
      return source.name
    }
  }

}