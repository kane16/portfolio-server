package pl.delukesoft.portfolioserver.application.resume

import org.springframework.stereotype.Component
import pl.delukesoft.blog.image.exception.ResumeNotFound
import pl.delukesoft.blog.image.exception.ResumeOperationNotAllowed
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.portfolio.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.application.portfolio.filter.PortfolioSearchMapper
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeHistoryDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeShortcutDTO
import pl.delukesoft.portfolioserver.application.resume.model.ResumeDTO
import pl.delukesoft.portfolioserver.application.resume.model.ValidationResultDTO
import pl.delukesoft.portfolioserver.application.skill.SkillDTO
import pl.delukesoft.portfolioserver.application.skill.SkillFacade
import pl.delukesoft.portfolioserver.application.skill.SkillMapper
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeSearchService
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.ResumeValidator
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService
import pl.delukesoft.portfolioserver.domain.validation.ResumeValidatorResult

@Component
class ResumeFacade(
  private val resumeService: ResumeService,
  private val resumeHistoryService: ResumeHistoryService,
  private val resumeSearchService: ResumeSearchService,
  private val portfolioSearchMapper: PortfolioSearchMapper,
  private val resumeMapper: ResumeMapper,
  private val userContext: UserContext,
  private val skillFacade: SkillFacade,
  private val skillMapper: SkillMapper,
  private val resumeValidator: ResumeValidator,
  private val validationMapper: ValidationMapper
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun getById(id: Long): ResumeDTO {
    return resumeMapper.mapResumeToDTO(resumeService.getResumeById(id, userContext.user))
  }

  fun getById(id: Long, portfolioSearch: PortfolioSearch? = null): Resume {
    val resumeById = resumeService.getResumeById(id, userContext.user)
    return getResumeWithOptionalFilter(resumeById, portfolioSearch)
  }

  fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): Resume {
    val defaultResume = resumeService.getDefaultCV(userContext.user)
    return getResumeWithOptionalFilter(defaultResume, portfolioSearch)
  }

  private fun getResumeWithOptionalFilter(
    defaultResume: Resume,
    portfolioSearch: PortfolioSearch?
  ): Resume {
    val resumeSearch = portfolioSearch?.let { portfolioSearchMapper.mapToSearch(it) }
    return when (resumeSearch) {
      null -> defaultResume
      else -> resumeSearchService.filterResumeWithCriteria(defaultResume, resumeSearch)
    }
  }

  fun getUserHistory(): ResumeHistoryDTO {
    val history = resumeHistoryService.findByUsername(currentUser.username)
    return resumeMapper.mapHistoryToDTO(history)
  }

  fun initiateResume(shortcut: ResumeShortcutDTO): Boolean {
    val resumeWithShortcutOnly = resumeMapper.mapShortcutDTOToResume(shortcut, currentUser)
    resumeService.addResume(resumeWithShortcutOnly)
    return true
  }

  fun unpublishResume(): Boolean {
    val publishedVersion = resumeHistoryService.findPublishedResumeVersion(currentUser.username)
    if (publishedVersion?.version == null) {
      throw ResumeOperationNotAllowed("No version has been published yet")
    }
    return resumeService.unpublishResume(publishedVersion, userContext.user?.username!!)
  }

  fun editResumeShortcut(id: Long, shortcut: ResumeShortcutDTO): Boolean {
    val resume = resumeService.getResumeById(id, currentUser)
    val resumeWithShortcutToModify = resumeMapper.mapShortcutDTOToResume(shortcut, currentUser, resume.id)
    return resumeService.editResumeShortcut(resumeWithShortcutToModify.id!!, resumeWithShortcutToModify.shortcut)
  }

  fun publishResume(version: Long): Boolean {
    val publishedVersion = resumeHistoryService.findPublishedResumeVersion(currentUser.username)
    val versionToPublish = resumeHistoryService.findVersionByIdAndUsername(version, currentUser.username)
    if (publishedVersion != null) {
      throw ResumeOperationNotAllowed("Published version already exists")
    }
    if (versionToPublish == null) {
      throw ResumeNotFound()
    }
    return resumeService.publishResume(versionToPublish, userContext.user?.username!!)
  }


  fun addSkillToResume(id: Long, skillName: String): Boolean {
    val skillToAdd = skillFacade.getSkill(skillName)
    val resumeToModify = resumeService.getResumeById(id, currentUser)
    return resumeService.addSkillToResume(resumeToModify, skillToAdd)
  }

  fun findSkillsByResumeId(resumeId: Long): List<SkillDTO> {
    val resume = resumeService.getResumeById(resumeId, userContext.user)
    return resume.skills.map { skillMapper.mapToDTO(it) }
  }

  fun validateResume(id: Long): ValidationResultDTO {
    val resume = resumeService.getResumeById(id, userContext.user)
    val validationResult = resumeValidator.validate(resume) as ResumeValidatorResult
    return validationMapper.mapResumeValidationResultToDTO(validationResult)
  }

}