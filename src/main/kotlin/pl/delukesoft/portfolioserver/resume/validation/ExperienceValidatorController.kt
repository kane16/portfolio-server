package pl.delukesoft.portfolioserver.resume.validation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.security.AuthRequired
import pl.delukesoft.portfolioserver.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.resume.skill.SkillDTO

@RestController
@RequestMapping("/resume/{id}/validate/experience")
@Tag(name = "Validation - Experience", description = "Experience field validation")
class ExperienceValidatorController(
  private val validationFacade: ValidationFacade
) {

  private val log = org.slf4j.LoggerFactory.getLogger(ExperienceValidatorController::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/business")
  @Operation(
    summary = "Validate business name",
    description = "Validate the business/company name for an experience entry"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateBusiness(
    @PathVariable("id") id: Long,
    @RequestBody business: String,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Experience Business")
    return validationFacade.validateBusiness(id, business)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/timeframe")
  @Operation(summary = "Validate experience timeframe", description = "Validate the timeframe for an experience entry")
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateExperienceTimeframe(
    @PathVariable("id") id: Long,
    @RequestBody timeframe: TimeframeDTO,
    @RequestHeader("Authorization") token: String?,
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Experience Timeframe")
    return validationFacade.validateExperienceTimeframe(id, timeframe)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/skills")
  @Operation(summary = "Validate experience skills", description = "Validate the skills list for an experience entry")
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateSkillsExperience(
    @PathVariable("id") id: Long,
    @RequestBody skills: List<SkillDTO>,
    @RequestHeader("Authorization") token: String?,
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Experience Skills")
    return validationFacade.validateExperienceSkills(id, skills)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping
  @Operation(summary = "Validate experience", description = "Validate a complete experience entry")
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateExperience(
    @PathVariable("id") id: Long,
    @RequestBody experience: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Experience")
    return validationFacade.validateExperience(id, experience)
  }

}