package pl.delukesoft.portfolioserver.portfolio

import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.delukesoft.portfolioserver.auth.AuthRequired
import pl.delukesoft.portfolioserver.portfolio.model.PortfolioDTO

@RestController
@RequestMapping("cv")
@Validated
class PortfolioController(
  private val portfolioFacade: PortfolioFacade,
) {

  private val log = LoggerFactory.getLogger(PortfolioController::class.java)

  @AuthRequired
  @GetMapping("/{id}")
  fun getCVById(
    @PathVariable("id") id: Long
  ): PortfolioDTO {
    log.info("Received request to fetch CV by id: {}", id)
    return portfolioFacade.getCvById(id)
  }

  @GetMapping
  fun getDefaultCv(): PortfolioDTO {
    log.info("Received request to fetch default CV")
    return portfolioFacade.getDefaultCV()
  }

}