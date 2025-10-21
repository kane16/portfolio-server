package pl.delukesoft.portfolioserver.application.resume.language

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.portfolio.model.LanguageDTO

@RestController
class LanguageController(
  private val languageFacade: LanguageFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/languages")
  @ResponseStatus(HttpStatus.CREATED)
  fun addLanguage(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody language: LanguageDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return languageFacade.addLanguageToResume(resumeId, language)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/languages/{languageId}")
  fun editLanguage(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("languageId") languageId: Long,
    @RequestBody language: LanguageDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return languageFacade.editLanguageInResume(resumeId, language, languageId)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/resume/edit/{resumeId}/languages/{languageId}")
  fun deleteLanguageInResumeById(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("languageId") languageId: Long,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return languageFacade.deleteLanguageFromResume(resumeId, languageId)
  }

}