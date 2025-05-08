package pl.delukesoft.portfolioserver.portfolio

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("cv")
@Validated
class PortfolioController(
  private val portfolioFacade: PortfolioFacade,
) {

  @GetMapping("/{id}")
  fun getCVById(
    @PathVariable("id") id: Long
  ): PortfolioDTO {
    return portfolioFacade.getCvById(id)
  }

  @GetMapping
  fun getDefaultCv(
  ): PortfolioDTO {
    return portfolioFacade.getDefaultCV()
  }

}