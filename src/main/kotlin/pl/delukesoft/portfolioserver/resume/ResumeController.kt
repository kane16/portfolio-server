package pl.delukesoft.portfolioserver.resume

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.authplugin.security.AuthRequired
import pl.delukesoft.portfolioserver.resume.history.ResumeHistoryDTO
import pl.delukesoft.portfolioserver.resume.shortcut.ResumeShortcutDTO

@RestController
@RequestMapping("/resume")
@Tag(name = "Resume", description = "Resume management and editing")
class ResumeController(
  private val resumeFacade: ResumeFacade,
) {

  private val log = LoggerFactory.getLogger(this::class.java)

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @GetMapping("/{id}")
  @Operation(summary = "Get resume by ID", description = "Retrieve a resume in edit form by its ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun getCVById(
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String?
  ): ResumeEditDTO {
    log.info("Received request to fetch Resume by id: {}", id)
    return resumeFacade.getEditDTOById(id)
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @GetMapping("/history")
  @Operation(summary = "Get resume history", description = "Retrieve the resume edit history for the current user")
  @SecurityRequirement(name = "Bearer Authentication")
  fun getHistoryByUser(
    @RequestHeader("Authorization") token: String?
  ): ResumeHistoryDTO {
    log.info("Received request to fetch CV history")
    return resumeFacade.getUserHistory()
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @PostMapping("/edit/init")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Initiate resume edit", description = "Start a new resume editing session")
  @SecurityRequirement(name = "Bearer Authentication")
  fun initiatePortfolioEdit(
    @Valid @RequestBody shortcut: ResumeShortcutDTO,
    @RequestHeader("Authorization") token: String?
  ): ResumeEditDTO {
    log.info("Received request to initiate portfolio edit")
    return resumeFacade.initiateResume(shortcut)
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @PutMapping("/edit/{id}")
  @Operation(summary = "Update resume shortcut", description = "Update the resume shortcut/summary information")
  @SecurityRequirement(name = "Bearer Authentication")
  fun updatePortfolioShortcut(
    @PathVariable("id") resumeId: Long,
    @Valid @RequestBody shortcut: ResumeShortcutDTO,
    @RequestHeader("Authorization") token: String?,
  ): ResumeEditDTO {
    log.info("Received request to update portfolio shortcut")
    return resumeFacade.editResumeShortcut(resumeId, shortcut)
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @PutMapping("/edit/{version}/publish")
  @Operation(summary = "Publish resume", description = "Publish a specific version of the resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun publishPortfolio(
    @PathVariable("version") version: Long,
    @RequestHeader("Authorization") token: String?,
  ): ResumeEditDTO {
    log.info("Received request to publish portfolio")
    return resumeFacade.publishResume(version)
  }

  @AuthRequired(role = "ROLE_AUTHOR", app = "portfolio")
  @PutMapping("/edit/unpublish")
  @Operation(summary = "Unpublish resume", description = "Unpublish the currently published resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun unpublishPortfolio(
    @RequestHeader("Authorization") token: String?,
  ): ResumeEditDTO {
    log.info("Received request to unpublish portfolio")
    return resumeFacade.unpublishResume()
  }

}