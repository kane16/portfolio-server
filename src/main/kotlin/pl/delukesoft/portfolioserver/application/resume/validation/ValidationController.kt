package pl.delukesoft.portfolioserver.application.resume.validation

import org.slf4j.LoggerFactory.getLogger
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO

@RestController
@RequestMapping("/resume/{id}/validate")
class ValidationController(
  private val validationFacade: ValidationFacade
) {

  private val log = getLogger(this::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping
  fun validateResume(
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String?
  ): ValidationResultDTO {
    log.info("Received request to validate Resume by id: {}", id)
    return validationFacade.validateResume(id)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/experience/business")
  fun validateBusiness(
    @PathVariable("id") id: Long,
    @RequestBody business: String,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Experience Business")
    return validationFacade.validateBusiness(id, business)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/experience/timeframe")
  fun validateExperienceTimeframe(
    @PathVariable("id") id: Long,
    @RequestBody timeframe: TimeframeDTO,
    @RequestHeader("Authorization") token: String?,
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Experience Timeframe")
    return validationFacade.validateExperienceTimeframe(id, timeframe)
  }


}