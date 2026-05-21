package pl.delukesoft.portfolioserver.resume.language

import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.portfolioserver.resume.ResumeService
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthor
import pl.delukesoft.portfolioserver.resume.language.exception.LanguageNotFound

@Component
class LanguageFacade(
  private val languageService: LanguageService,
  private val resumeService: ResumeService,
  private val userContext: AuthContext<PortfolioAuthor>
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addLanguageToResume(resumeId: Long, languageDTO: LanguageDTO): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val language = Language(
      null,
      languageDTO.name,
      LanguageLevel.entries.first { it.name == languageDTO.level }
    )
    return languageService.addLanguageToResume(resumeVersion, language)
  }

  fun editLanguageInResume(resumeId: Long, languageDTO: LanguageDTO, languageId: Long): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val language = Language(
      languageId,
      languageDTO.name,
      LanguageLevel.entries.first { it.name == languageDTO.level }
    )
    return languageService.editLanguageInResume(resumeVersion, language)
  }

  fun deleteLanguageFromResume(resumeId: Long, languageId: Long): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val languageToDelete =
      resumeVersion.resume.languages.find { it.id == languageId }
        ?: throw LanguageNotFound("id: $languageId")
    return languageService.deleteLanguageFromResume(resumeVersion, languageToDelete)
  }

}