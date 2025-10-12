package pl.delukesoft.portfolioserver.domain.resume.language

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class LanguageService(
  private val resumeModifyRepository: ResumeModifyRepository,
  private val generatorService: GeneratorService
) {

  fun addLanguageToResume(resume: Resume, language: Language): Boolean {
    val languages = resume.languages + language.copy(
      id = generatorService.getAndIncrement("language")
    )
    return resumeModifyRepository.changeLanguagesInResume(languages, resume)
  }

  fun editLanguageInResume(resume: Resume, language: Language): Boolean {
    val languages = resume.languages.map {
      if (it.name == language.name) language else it
    }
    return resumeModifyRepository.changeLanguagesInResume(languages, resume)
  }

  fun deleteLanguageFromResume(resume: Resume, languageToDelete: Language): Boolean {
    val languages = resume.languages.filter { it.id == languageToDelete.id }
    return resumeModifyRepository.changeLanguagesInResume(languages, resume)
  }

}