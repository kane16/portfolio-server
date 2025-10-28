package pl.delukesoft.portfolioserver.application.resume.validation

import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO

@RestController
@RequestMapping("/resume/{id}/validate/experience")
class ExperienceValidatorController(
  private val validationFacade: ValidationFacade
) {

  private val log = org.slf4j.LoggerFactory.getLogger(ExperienceValidatorController::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/business")
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
  fun validateExperience(
    @PathVariable("id") id: Long,
    @RequestBody experience: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Experience")
    return validationFacade.validateExperience(id, experience)
  }

}