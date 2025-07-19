package pl.delukesoft.portfolioserver.application.portfolio

import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO

@RestController
@RequestMapping("portfolio")
@Validated
class PortfolioController(
  private val portfolioFacade: PortfolioFacade,
) {

  private val log = LoggerFactory.getLogger(PortfolioController::class.java)

  @AuthRequired("ROLE_ADMIN")
  @GetMapping("/{id}")
  fun getCVById(
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String?,
  ): PortfolioDTO {
    log.info("Received request to fetch CV by id: {}", id)
    return portfolioFacade.getCvById(id)
  }

  @AuthRequired("ROLE_ADMIN")
  @PostMapping("/{id}")
  fun getCVByIdWithSearch(
    @PathVariable("id") id: Long,
    @Validated @RequestBody search: PortfolioSearch?,
    @RequestHeader("Authorization") token: String?,
  ): PortfolioDTO {
    log.info("Received request to fetch CV by id: {} with search", id)
    return portfolioFacade.getCvById(id, search)
  }

  @GetMapping
  fun getDefaultCv(): PortfolioDTO {
    log.info("Received request to fetch default CV")
    return portfolioFacade.getDefaultCV()
  }

  @PostMapping
  fun getDefaultCv(
    @Validated @RequestBody search: PortfolioSearch?
  ): PortfolioDTO {
    log.info("Received request to fetch default CV with search")
    return portfolioFacade.getDefaultCV(search)
  }

}