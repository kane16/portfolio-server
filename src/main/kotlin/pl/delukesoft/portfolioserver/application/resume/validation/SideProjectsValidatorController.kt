package pl.delukesoft.portfolioserver.application.resume.validation

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO

@RestController
@RequestMapping("/resume/{id}/validate/sideProjects")
class SideProjectsValidatorController(
  private val validationFacade: ValidationFacade
) {

  private val log = LoggerFactory.getLogger(SideProjectsValidatorController::class.java)

  @AuthRequired("ROLE_CANDIDATE")
  @PostMapping("/business")
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
  fun validateSideProject(
    @PathVariable("id") id: Long,
    @RequestBody sideProject: ExperienceDTO,
    @RequestHeader("Authorization") token: String?
  ): SimpleValidationResultDTO {
    log.info("Received request to validate Side Projects")
    return validationFacade.validateSideProject(id, sideProject)
  }

}