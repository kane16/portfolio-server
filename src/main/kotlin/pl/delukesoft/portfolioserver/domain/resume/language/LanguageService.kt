package pl.delukesoft.portfolioserver.domain.resume.language

import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.LanguageNotFound
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class LanguageService(
  private val languageRepository: LanguageRepository,
  private val generatorService: GeneratorService
) {

  fun saveAll(languages: List<Language>): List<Language> {
    return languages.map { save(it) }
  }

  fun save(language: Language): Language {
    if (language.id == null) {
      val generatedId = generatorService.getAndIncrement("language")
      return languageRepository.save(language.copy(id = generatedId))
    }
    return languageRepository.findByName(language.name) ?: throw LanguageNotFound(language.name)
  }

}