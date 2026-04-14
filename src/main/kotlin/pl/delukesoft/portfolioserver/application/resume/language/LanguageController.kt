package pl.delukesoft.portfolioserver.application.resume.language

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.portfolio.model.LanguageDTO

@RestController
@Tag(name = "Resume - Languages", description = "Language entries within a resume")
class LanguageController(
  private val languageFacade: LanguageFacade
) {

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/resume/edit/{resumeId}/languages")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Add language", description = "Add a language entry to a resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun addLanguage(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody language: LanguageDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return languageFacade.addLanguageToResume(resumeId, language)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/resume/edit/{resumeId}/languages/{languageId}")
  @Operation(summary = "Edit language", description = "Edit a language entry by ID")
  @SecurityRequirement(name = "Bearer Authentication")
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
  @Operation(summary = "Delete language", description = "Delete a language entry by ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun deleteLanguageInResumeById(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("languageId") languageId: Long,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return languageFacade.deleteLanguageFromResume(resumeId, languageId)
  }

}