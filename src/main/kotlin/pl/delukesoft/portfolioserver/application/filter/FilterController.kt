package pl.delukesoft.portfolioserver.application.filter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/filter")
class FilterController(
  private val filterFacade: FilterFacade
) {

  @GetMapping("/skills")
  fun getSkills(): List<String> {
    return filterFacade.getAllSkills()
  }

  @GetMapping("/business")
  fun getBusiness(): List<String> {
    return filterFacade.getAllBusiness()
  }

  @GetMapping("/domains")
  fun getDomains(): List<String> {
    return filterFacade.getAllDomains()
  }

}