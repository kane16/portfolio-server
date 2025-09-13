package pl.delukesoft.portfolioserver.application.portfolio

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.portfolio.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioHistoryDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioShortcutDTO

@RestController
@RequestMapping("portfolio")
@Validated
class PortfolioController(
  private val portfolioFacade: PortfolioFacade,
) {

  private val log = LoggerFactory.getLogger(PortfolioController::class.java)

  @AuthRequired("ROLE_ADMIN", "ROLE_CANDIDATE")
  @PostMapping("/{id}")
  fun getCVByIdWithSearch(
    @PathVariable("id") id: Long,
    @Validated @RequestBody(required = false) search: PortfolioSearch?,
    @RequestHeader("Authorization") token: String?,
  ): PortfolioDTO {
    log.info("Received request to fetch CV by id: {} with search", id)
    return portfolioFacade.getCvById(id, search)
  }

  @PostMapping
  fun getDefaultCv(
    @Valid @RequestBody(required = false) search: PortfolioSearch?
  ): PortfolioDTO {
    log.info("Received request to fetch default CV with search")
    return portfolioFacade.getDefaultCV(search)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @GetMapping("/history")
  fun getHistoryByUser(
    @RequestHeader("Authorization") token: String?
  ): PortfolioHistoryDTO {
    log.info("Received request to fetch CV history")
    return portfolioFacade.getUserHistory()
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/edit/init")
  @ResponseStatus(HttpStatus.CREATED)
  fun initiatePortfolioEdit(
    @Valid @RequestBody shortcut: PortfolioShortcutDTO,
    @RequestHeader("Authorization") token: String?
  ): Boolean {
    log.info("Received request to initiate portfolio edit")
    return portfolioFacade.initiatePortfolio(shortcut)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/edit/{id}")
  fun updatePortfolioShortcut(
    @PathVariable("id") id: Long,
    @Valid @RequestBody shortcut: PortfolioShortcutDTO,
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to update portfolio shortcut")
    return portfolioFacade.editPortfolio(id, shortcut)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/edit/{version}/publish")
  fun publishPortfolio(
    @PathVariable("version") version: Long,
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to publish portfolio")
    return portfolioFacade.publishPortfolio(version)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PutMapping("/edit/unpublish")
  fun unpublishPortfolio(
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to unpublish portfolio")
    return portfolioFacade.unpublishPortfolio()
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/edit/{version}/skills")
  @ResponseStatus(HttpStatus.CREATED)
  fun addSkillToPortfolio(
    @PathVariable("version") version: Long,
    @RequestBody skillName: String,
    @RequestHeader("Authorization") token: String?,
  ): Boolean {
    log.info("Received request to add skill to portfolio")
    return portfolioFacade.addSkillToPortfolio(version, skillName)
  }

}