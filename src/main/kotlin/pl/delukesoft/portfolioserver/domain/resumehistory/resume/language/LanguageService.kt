package pl.delukesoft.portfolioserver.domain.resumehistory.resume.language

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.print.GeneratorService

@Service
class LanguageService(
  private val languageWriteRepository: LanguageWriteRepository,
  private val generatorService: GeneratorService
) {

  fun saveAll(languages: List<Language>): List<Language> {
    return languages.map { save(it) }
  }

  fun save(language: Language): Language {
    if (language.id == null) {
      val generatedId = generatorService.getAndIncrement("language")
      return languageWriteRepository.save(language.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

}