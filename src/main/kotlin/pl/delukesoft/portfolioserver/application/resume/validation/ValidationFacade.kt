package pl.delukesoft.portfolioserver.application.resume.validation

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.resume.ValidationMapper
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.ResumeValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.skill.exception.SkillNotFound
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator
import pl.delukesoft.portfolioserver.domain.validation.ResumeValidatorResult
import pl.delukesoft.portfolioserver.domain.validation.Validator
import pl.delukesoft.portfolioserver.utility.exception.InvalidMappingException

@Component
class ValidationFacade(
  private val resumeService: ResumeService,
  private val resumeValidator: ResumeValidator,
  private val businessValidator: Validator<Business>,
  private val userContext: UserContext,
  private val validationMapper: ValidationMapper,
  @Qualifier("consecutiveTimeframeValidator") private val experienceTimeframeValidator: TimeframeValidator,
  @Qualifier("lenientTimeframeValidator") private val lenientTimeframeValidator: TimeframeValidator,
  private val experienceSkillsValidator: Validator<SkillExperience>,
  @Qualifier("jobExperienceValidator") private val jobValidator: Validator<Experience>,
  @Qualifier("sideProjectsValidator") private val sideProjectsValidator: Validator<Experience>,
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun validateResume(id: Long): ValidationResultDTO {
    val resume = resumeService.getResumeById(id, currentUser)
    val validationResult = resumeValidator.validate(resume) as ResumeValidatorResult
    return validationMapper.mapResumeValidationResultToDTO(validationResult)
  }

  fun validateBusiness(id: Long, business: String): SimpleValidationResultDTO {
    resumeService.getResumeById(id, currentUser)
    val business = Business(business)
    val validationResult = businessValidator.validate(business)
    return validationMapper.mapValidationResultToDTO(validationResult, "business")
  }

  fun validateExperienceTimeframe(id: Long, timeframe: TimeframeDTO): SimpleValidationResultDTO {
    val resume = resumeService.getResumeById(id, currentUser)
    val addedTimeframe = Timeframe(timeframe.start, timeframe.end)
    val validationResult = (resume.experience.map { it.timeframe } + addedTimeframe).sortedBy { it.start }
    val validationResults = experienceTimeframeValidator.validateList(validationResult)
    return validationMapper.mapValidationResultToDTO(validationResults, "timeframe")
  }

  fun validateSideProjectTimeframe(id: Long, timeframe: TimeframeDTO): SimpleValidationResultDTO {
    val resume = resumeService.getResumeById(id, currentUser)
    val addedTimeframe = Timeframe(timeframe.start, timeframe.end)
    val validationResult = (resume.sideProjects.map { it.timeframe } + addedTimeframe).sortedBy { it.start }
    val validationResults = lenientTimeframeValidator.validateList(validationResult)
    return validationMapper.mapValidationResultToDTO(validationResults, "timeframe")
  }

  fun validateExperienceSkills(id: Long, experienceSkillsDTO: List<SkillDTO>): SimpleValidationResultDTO {
    val resume = resumeService.getResumeById(id, currentUser)
    val experienceSkills = experienceSkillsDTO.map { skillExperienceDTO ->
      SkillExperience(
        resume.skills.find { it.name == skillExperienceDTO.name } ?: throw SkillNotFound(skillExperienceDTO.name),
        skillExperienceDTO.level,
        skillExperienceDTO.detail ?: throw InvalidMappingException("Detail is expected to be provided")
      )
    }
    val validationResults = experienceSkillsValidator.validateList(experienceSkills)
    return validationMapper.mapValidationResultToDTO(validationResults, "skillExperience")
  }

  fun validateExperience(resumeId: Long, experienceDTO: ExperienceDTO): SimpleValidationResultDTO {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val experience = Experience(
      null,
      Business(experienceDTO.business),
      experienceDTO.position,
      experienceDTO.summary,
      experienceDTO.description,
      Timeframe(experienceDTO.timespan.start, experienceDTO.timespan.end),
      experienceDTO.skills.map { skillExperienceDTO ->
        SkillExperience(
          resume.skills.find { it.name == skillExperienceDTO.name }!!,
          skillExperienceDTO.level,
          skillExperienceDTO.detail ?: throw InvalidMappingException("Detail is expected to be provided")
        )
      }
    )
    val experiences = (resume.experience + experience).sortedBy { it.timeframe.start }
    val validationResult = jobValidator.validateList(experiences)
    return validationMapper.mapValidationResultToDTO(validationResult, "experience")
  }

  fun validateSideProject(resumeId: Long, sideProjectDTO: ExperienceDTO): SimpleValidationResultDTO {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val sideProject = Experience(
      null,
      Business(sideProjectDTO.business),
      sideProjectDTO.position,
      sideProjectDTO.summary,
      sideProjectDTO.description,
      Timeframe(sideProjectDTO.timespan.start, sideProjectDTO.timespan.end),
      sideProjectDTO.skills.map { skillExperienceDTO ->
        SkillExperience(
          resume.skills.find { it.name == skillExperienceDTO.name }!!,
          skillExperienceDTO.level,
          skillExperienceDTO.detail ?: throw InvalidMappingException("Detail is expected to be provided")
        )
      }
    )
    val experiences = (resume.sideProjects + sideProject).sortedBy { it.timeframe.start }
    val validationResult = jobValidator.validateList(experiences)
    return validationMapper.mapValidationResultToDTO(validationResult, "sideProject")
  }

}