package pl.delukesoft.portfolioserver.application.resume.validation

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.resume.ValidationMapper
import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.ResumeValidator
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.domain.resume.timespan.TimeframeValidator
import pl.delukesoft.portfolioserver.domain.validation.ResumeValidatorResult
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Component
class ValidationFacade(
  private val resumeService: ResumeService,
  private val resumeValidator: ResumeValidator,
  private val businessValidator: Validator<Business>,
  private val userContext: UserContext,
  private val validationMapper: ValidationMapper,
  @Qualifier("consecutiveTimeframeValidator") private val experienceTimeframeValidator: TimeframeValidator
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
    val addedTimeframe = Timeframe(timeframe.from, timeframe.to)
    val validationResult = (resume.experience.map { it.timeframe } + addedTimeframe).sortedBy { it.start }
    val validationResults = experienceTimeframeValidator.validateList(validationResult)
    return validationMapper.mapValidationResultToDTO(validationResults, "timeframe")
  }

}