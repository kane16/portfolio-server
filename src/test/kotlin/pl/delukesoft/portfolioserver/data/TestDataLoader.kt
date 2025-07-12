package pl.delukesoft.portfolioserver.data

import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import pl.delukesoft.blog.image.Image
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.model.Business
import pl.delukesoft.portfolioserver.domain.resume.model.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.model.WorkLanguage
import pl.delukesoft.portfolioserver.domain.resume.read.ResumeService

@Component
@Profile("test")
class TestDataLoader(
  private val resumeService: ResumeService,
  val resourceLoader: ResourceLoader
): ApplicationRunner {

  override fun run(args: ApplicationArguments?) {
    val businesses = extractEntity("data/business.csv", ::mapLineToBusiness)
    val images = extractEntity("data/image.csv", ::mapLineToImages)
    val users = extractEntity("data/user.csv", ::mapLineToUsers)
    val workLanguages = extractEntity("data/languages.csv", ::mapLineToLanguages)
    println()
  }

  private fun <T> extractEntity(filename: String, mapper: (Array<String>, Map<String, Int>) -> T): List<T> {
    val parser = CsvParser(CsvParserSettings())
    parser.beginParsing(resourceLoader.getResource("classpath:${filename}").inputStream)
    val fileContent = parser.parseAll()
    val keys = readKeysPosition(fileContent.first())
    return fileContent.takeLast(fileContent.size - 1).map {
      line -> mapLineToEntity(line, keys, mapper)
    }
  }

  private fun <T> mapLineToEntity(line: Array<String>, keys: Map<String, Int>, mapper: (Array<String>, Map<String, Int>) -> T): T {
    return mapper(line, keys)
  }

  private fun mapLineToBusiness(values: Array<String>, keys: Map<String, Int>): Business {
    return Business(values[keys["id"] ?: 0].toLong(), values[keys["name"] ?: 0])
  }

  private fun mapLineToImages(values: Array<String>, keys: Map<String, Int>): Image {
    return Image(values[keys["id"] ?: 0].toLong(), values[keys["name"] ?: 0], values[keys["src"] ?: 0])
  }

  private fun mapLineToUsers(values: Array<String>, keys: Map<String, Int>): User {
    return User(values[keys["id"] ?: 0].toLong(), values[keys["username"] ?: 0], values[keys["email"] ?: 0], values[keys["roles"] ?: 0].split(','))
  }

  private fun mapLineToLanguages(values: Array<String>, keys: Map<String, Int>): WorkLanguage {
    val languageLevel: LanguageLevel = LanguageLevel.entries.find { it.level == values[keys["level"] ?: 0].toInt() } ?: LanguageLevel.A1
    return WorkLanguage(values[keys["name"] ?: 0], languageLevel)
  }

  private fun readKeysPosition(line: Array<String>): Map<String, Int> {
    return line.mapIndexed { index, key -> key to index }.toMap()
  }

}