package pl.delukesoft.portfolioserver.application.resume.validation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.education.EducationDTO

@RestController
@RequestMapping("/resume/{id}/validate/education")
@Tag(name = "Validation - Education", description = "Education field validation")
class EducationValidatorController(
  private val validationFacade: ValidationFacade
) {

  private val log = org.slf4j.LoggerFactory.getLogger(EducationValidatorController::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping
  @Operation(summary = "Validate education", description = "Validate an education entry for a resume")
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateEducation(
    @PathVariable("id") id: Long,
    @RequestBody education: EducationDTO,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Education")
    return validationFacade.validateEducation(id, education)
  }
}