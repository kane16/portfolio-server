package pl.delukesoft.portfolioserver.application.resume.validation

import org.slf4j.LoggerFactory.getLogger
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

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


}