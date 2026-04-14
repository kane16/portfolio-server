package pl.delukesoft.portfolioserver.application.resume.validation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO

@RestController
@RequestMapping("/resume/{id}/validate/sideProjects")
@Tag(name = "Validation - Side Projects", description = "Side project field validation")
class SideProjectsValidatorController(
  private val validationFacade: ValidationFacade
) {

  private val log = LoggerFactory.getLogger(SideProjectsValidatorController::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/business")
  @Operation(
    summary = "Validate side project business",
    description = "Validate the business/project name for a side project entry"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateBusiness(
    @PathVariable("id") id: Long,
    @RequestBody business: String,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Side Projects Business")
    return validationFacade.validateBusiness(id, business)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/timeframe")
  @Operation(
    summary = "Validate side project timeframe",
    description = "Validate the timeframe for a side project entry"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateSideProjectTimeframe(
    @PathVariable("id") id: Long,
    @RequestBody timeframe: TimeframeDTO,
    @RequestHeader("Authorization") token: String?,
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Side Projects Timeframe")
    return validationFacade.validateSideProjectTimeframe(id, timeframe)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/skills")
  @Operation(
    summary = "Validate side project skills",
    description = "Validate the skills list for a side project entry"
  )
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateSkillsSideProject(
    @PathVariable("id") id: Long,
    @RequestBody skills: List<SkillDTO>,
    @RequestHeader("Authorization") token: String?,
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Side Projects Skills")
    return validationFacade.validateExperienceSkills(id, skills)
  }

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping
  @Operation(summary = "Validate side project", description = "Validate a complete side project entry")
  @SecurityRequirement(name = "Bearer Authentication")
  fun validateSideProject(
    @PathVariable("id") id: Long,
    @RequestBody sideProject: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Side Projects")
    return validationFacade.validateSideProject(id, sideProject)
  }

}