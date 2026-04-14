package pl.delukesoft.portfolioserver.application.portfolio

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO

@RestController
@RequestMapping("cv")
@Validated
@Tag(name = "Portfolio", description = "Public portfolio/CV endpoints")
class PortfolioController(
  private val portfolioFacade: PortfolioFacade,
) {

  private val log = LoggerFactory.getLogger(PortfolioController::class.java)

  @AuthRequired("ROLE_ADMIN", "ROLE_CANDIDATE")
  @PostMapping("/{id}")
  @Operation(
    summary = "Get CV by ID",
    description = "Retrieve a portfolio/CV by its ID. Requires ADMIN or CANDIDATE role."
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun getCVByIdWithSearch(
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String?,
  ): PortfolioDTO {
    log.info("Received request to fetch CV by id: {} with search", id)
    return portfolioFacade.getCvById(id)
  }

  @PostMapping
  @Operation(summary = "Get default CV", description = "Retrieve the default public portfolio/CV")
  fun getDefaultCv(): PortfolioDTO {
    log.info("Received request to fetch default CV with search")
    return portfolioFacade.getDefaultCV()
  }

}