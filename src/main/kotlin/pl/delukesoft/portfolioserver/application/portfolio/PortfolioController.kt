package pl.delukesoft.portfolioserver.application.portfolio

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.filter.PortfolioSearch
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

  @AuthRequired("ROLE_ADMIN")
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
    return portfolioFacade.initiatePortfolio(shortcut)
  }

}