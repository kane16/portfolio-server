package pl.delukesoft.portfolioserver.resume.validation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory.getLogger
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.security.AuthRequired

@RestController
@RequestMapping("/resume/{id}/validate")
@Tag(name = "Validation", description = "Resume validation endpoints")
class ValidationController(
  private val validationFacade: ValidationFacade
) {

  private val log = getLogger(this::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping
  @Operation(summary = "Validate resume", description = "Run full validation on a resume by ID")
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateResume(
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String?
  ): ValidationResultDTO {
    log.info("Received request to validate Resume by id: {}", id)
    return validationFacade.validateResume(id)
  }


}