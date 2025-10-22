package pl.delukesoft.portfolioserver.application.resume.education

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.resume.ResumeMapper
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.education.EducationService

@Component
class EducationFacade(
  private val resumeService: ResumeService,
  private val userContext: UserContext,
  private val resumeMapper: ResumeMapper,
  private val educationService: EducationService
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addEducationToResume(resumeId: Long, dto: EducationDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val education = resumeMapper.mapDTOToEducation(dto)

    return educationService.addEducationToResume(education, resume)
  }

  fun modifyEducationInResume(resumeId: Long, education: EducationDTO, educationId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val educationEntry = resumeMapper.mapDTOToEducation(education).copy(id = educationId)

    return educationService.modifyEducationInResume(educationEntry, resume)
  }

  fun deleteEducationFromResume(resumeId: Long, educationId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    return educationService.deleteEducationFromResume(educationId, resume)
  }


}