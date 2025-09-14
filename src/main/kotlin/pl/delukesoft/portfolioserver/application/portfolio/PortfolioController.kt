package pl.delukesoft.portfolioserver.application.portfolio

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.portfolio.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO

@RestController
@RequestMapping("cv")
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

}