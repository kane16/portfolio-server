package pl.delukesoft.portfolioserver.resume.education

import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthor
import pl.delukesoft.portfolioserver.resume.ResumeMapper
import pl.delukesoft.portfolioserver.resume.ResumeService

@Component
class EducationFacade(
  private val resumeService: ResumeService,
  private val authContext: AuthContext<PortfolioAuthor>,
  private val resumeMapper: ResumeMapper,
  private val educationService: EducationService
) {

  private val currentUser
    get() = requireNotNull(authContext.user) { "Authenticated user is required" }

  fun addEducationToResume(resumeId: Long, dto: EducationDTO): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val education = resumeMapper.mapDTOToEducation(dto)

    return educationService.addEducationToResume(education, resumeVersion)
  }

  fun modifyEducationInResume(resumeId: Long, education: EducationDTO, educationId: Long): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val educationEntry = resumeMapper.mapDTOToEducation(education).copy(id = educationId)

    return educationService.modifyEducationInResume(educationEntry, resumeVersion)
  }

  fun deleteEducationFromResume(resumeId: Long, educationId: Long): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    return educationService.deleteEducationFromResume(educationId, resumeVersion)
  }


}