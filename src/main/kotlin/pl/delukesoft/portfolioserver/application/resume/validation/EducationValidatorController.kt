package pl.delukesoft.portfolioserver.application.resume.validation

import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.education.EducationDTO

@RestController
@RequestMapping("/resume/{id}/validate/education")
class EducationValidatorController(
  private val validationFacade: ValidationFacade
) {

  private val log = org.slf4j.LoggerFactory.getLogger(EducationValidatorController::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping
  fun validateEducation(
    @PathVariable("id") id: Long,
    @RequestBody education: EducationDTO,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Education")
    return validationFacade.validateEducation(id, education)
  }
}