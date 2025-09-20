package pl.delukesoft.portfolioserver.application.resume

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeHistoryDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeShortcutDTO
import pl.delukesoft.portfolioserver.application.resume.model.ResumeDTO
import pl.delukesoft.portfolioserver.application.resume.model.ValidationResultDTO
import pl.delukesoft.portfolioserver.application.skill.SkillDTO

@RestController
@RequestMapping("/resume")
class ResumeController(
  private val resumeFacade: ResumeFacade,
) {

  private val log = LoggerFactory.getLogger(this::class.java)

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

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/edit/{resumeId}/skills")
  @ResponseStatus(HttpStatus.CREATED)
  fun addSkillToResume(
    @PathVariable("resumeId") resumeId: Long,
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to add skill to portfolio")
    return resumeFacade.addSkillToResume(resumeId, skill)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @GetMapping("/{id}")
  fun getCVById(
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String?
  ): ResumeDTO {
    log.info("Received request to fetch Resume by id: {}", id)
    return resumeFacade.getById(id)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @GetMapping("/{id}/validate")
  fun validate(
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String?
  ): ValidationResultDTO {
    log.info("Received request to validate Resume by id: {}", id)
    return resumeFacade.validateResume(id)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @DeleteMapping("/edit/{resumeId}/skills/{skillName}")
  fun deleteSkill(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("skillName") skillName: String,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return resumeFacade.deleteSkillFromResume(resumeId, skillName)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/edit/{resumeId}/skills/{skillName}")
  fun editSkill(
    @PathVariable("resumeId") resumeId: Long,
    @PathVariable("skillName") skillName: String,
    @RequestBody skill: SkillDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    return resumeFacade.editSkillWithName(resumeId, skillName, skill)
  }


}