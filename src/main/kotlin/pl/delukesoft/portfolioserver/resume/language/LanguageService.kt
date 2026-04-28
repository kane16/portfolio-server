package pl.delukesoft.portfolioserver.resume.language

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.resume.history.ResumeVersion
import pl.delukesoft.portfolioserver.platform.sequence.GeneratorService

@Service
class LanguageService(
  private val resumeModifyRepository: ResumeModifyRepository,
  private val generatorService: GeneratorService
) {

  fun addLanguageToResume(resumeVersion: ResumeVersion, language: Language): Boolean {
    val resume = resumeVersion.resume
    val languages = resume.languages + language.copy(
      id = generatorService.getAndIncrement("language")
    )
    return resumeModifyRepository.changeLanguagesInResume(languages, resumeVersion)
  }

  fun editLanguageInResume(resumeVersion: ResumeVersion, language: Language): Boolean {
    val resume = resumeVersion.resume
    val languages = resume.languages.map {
      if (it.id == language.id) language else it
    }
    return resumeModifyRepository.changeLanguagesInResume(languages, resumeVersion)
  }

  fun deleteLanguageFromResume(resumeVersion: ResumeVersion, languageToDelete: Language): Boolean {
    val resume = resumeVersion.resume
    val languages = resume.languages.filter { it.id != languageToDelete.id }
    return resumeModifyRepository.changeLanguagesInResume(languages, resumeVersion)
  }

}