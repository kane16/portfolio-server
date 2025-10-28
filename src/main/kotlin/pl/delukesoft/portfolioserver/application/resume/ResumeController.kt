package pl.delukesoft.portfolioserver.application.resume

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeHistoryDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeShortcutDTO
import pl.delukesoft.portfolioserver.application.resume.model.ResumeEditDTO

@RestController
@RequestMapping("/resume")
class ResumeController(
  private val resumeFacade: ResumeFacade,
) {

  private val log = LoggerFactory.getLogger(this::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @GetMapping("/{id}")
  fun getCVById(
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String?
  ): ResumeEditDTO {
    log.info("Received request to fetch Resume by id: {}", id)
    return resumeFacade.getById(id)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @GetMapping("/history")
  fun getHistoryByUser(
    @RequestHeader("Authorization") token: String?
  ): ResumeHistoryDTO {
    log.info("Received request to fetch CV history")
    return resumeFacade.getUserHistory()
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/edit/init")
  @ResponseStatus(HttpStatus.CREATED)
  fun initiatePortfolioEdit(
    @Valid @RequestBody shortcut: ResumeShortcutDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    log.info("Received request to initiate portfolio edit")
    return resumeFacade.initiateResume(shortcut)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/edit/{id}")
  fun updatePortfolioShortcut(
    @PathVariable("id") resumeId: Long,
    @Valid @RequestBody shortcut: ResumeShortcutDTO,
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to update portfolio shortcut")
    return resumeFacade.editResumeShortcut(resumeId, shortcut)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/edit/{version}/publish")
  fun publishPortfolio(
    @PathVariable("version") version: Long,
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to publish portfolio")
    return resumeFacade.publishResume(version)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/edit/unpublish")
  fun unpublishPortfolio(
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to unpublish portfolio")
    return resumeFacade.unpublishResume()
  }

}