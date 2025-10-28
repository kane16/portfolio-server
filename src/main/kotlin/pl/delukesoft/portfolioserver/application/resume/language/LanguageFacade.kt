package pl.delukesoft.portfolioserver.application.resume.language

import org.springframework.stereotype.Component
import pl.delukesoft.blog.image.exception.LanguageNotFound
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.portfolio.model.LanguageDTO
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageService

@Component
class LanguageFacade(
  private val languageService: LanguageService,
  private val resumeService: ResumeService,
  private val userContext: UserContext
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addLanguageToResume(resumeId: Long, language: LanguageDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val language = Language(
      null,
      language.name,
      LanguageLevel.entries.first { it.name == language.level }
    )
    return languageService.addLanguageToResume(resume, language)
  }

  fun editLanguageInResume(resumeId: Long, language: LanguageDTO, languageId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val language = Language(
      languageId,
      language.name,
      LanguageLevel.entries.first { it.name == language.level }
    )
    return languageService.editLanguageInResume(resume, language)
  }

  fun deleteLanguageFromResume(resumeId: Long, languageId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val languageToDelete = resume.languages.find { it.id == languageId } ?: throw LanguageNotFound("id: $languageId")
    return languageService.deleteLanguageFromResume(resume, languageToDelete)
  }

}