package pl.delukesoft.portfolioserver.application.resume

import org.springframework.stereotype.Component
import pl.delukesoft.blog.image.exception.LanguageNotFound
import pl.delukesoft.blog.image.exception.ResumeNotFound
import pl.delukesoft.blog.image.exception.ResumeOperationNotAllowed
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.portfolio.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.application.portfolio.filter.PortfolioSearchMapper
import pl.delukesoft.portfolioserver.application.portfolio.model.LanguageDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeHistoryDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeShortcutDTO
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.application.resume.model.ResumeEditDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillMapper
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeSearchService
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.experience.ExperienceService
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyService
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageService
import pl.delukesoft.portfolioserver.domain.resume.sideProject.SideProjectService
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillService
import pl.delukesoft.portfolioserver.domain.resume.skill.exception.SkillNotFound
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryService

@Component
class ResumeFacade(
  private val resumeService: ResumeService,
  private val resumeHistoryService: ResumeHistoryService,
  private val resumeSearchService: ResumeSearchService,
  private val portfolioSearchMapper: PortfolioSearchMapper,
  private val resumeMapper: ResumeMapper,
  private val userContext: UserContext,
  private val skillMapper: SkillMapper,
  private val experienceService: ExperienceService,
  private val hobbyService: HobbyService,
  private val languageService: LanguageService,
  private val sideProjectService: SideProjectService,
  private val skillService: SkillService,
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  private val currentAuthor
    get() = requireNotNull(userContext.author) { "Authenticated author is required" }

  fun getById(id: Long, portfolioSearch: PortfolioSearch? = null): Resume {
    val resumeById = resumeService.getResumeById(id, userContext.user)
    return getResumeWithOptionalFilter(resumeById, portfolioSearch)
  }

  fun getById(id: Long): ResumeEditDTO {
    return resumeMapper.mapResumeToEditDTO(resumeService.getResumeById(id, userContext.user))
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
    val shortcut = resumeMapper.mapShortcutDTOToResume(shortcut, currentUser)
    resumeService.addResume(shortcut)
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
    val shortcut = resumeMapper.mapShortcutDTOToResume(shortcut, currentUser)
    return resumeService.editResumeShortcut(resume, shortcut)
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


  fun addSkillToResume(id: Long, skill: SkillDTO): Boolean {
    val skillToAdd = skillMapper.mapToSkill(skill, currentAuthor.domains)
    val resumeToModify = resumeService.getResumeById(id, currentUser)
    return skillService.addSkillToResume(resumeToModify, skillToAdd)
  }

  fun findSkillsByResumeId(resumeId: Long): List<SkillDTO> {
    val resume = resumeService.getResumeById(resumeId, userContext.user)
    return resume.skills.map { skillMapper.mapToDTO(it) }
  }

  fun deleteSkillFromResume(resumeId: Long, skillNameToRemove: String): Boolean {
    val resume = resumeService.getResumeById(resumeId, userContext.user)
    val skillToRemove = resume.skills.find { it.name == skillNameToRemove } ?: throw SkillNotFound(skillNameToRemove)
    return skillService.deleteSkillFromResume(resume, skillToRemove)
  }

  fun editSkillWithName(resumeId: Long, skillName: String, skill: SkillDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, userContext.user)
    val skillToEdit = resume.skills.find { it.name == skillName } ?: throw SkillNotFound(skillName)
    val skillUpdate = skillMapper.mapToSkill(skill, currentAuthor.domains)
    return skillService.editSkill(resume, skillToEdit, skillUpdate)
  }

  fun addExperienceToResume(resumeId: Long, experience: ExperienceDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resume.skills
    val experienceToAdd = resumeMapper.mapExperienceDTOToResume(experience, resumeSkills)
    return experienceService.addExperienceToResume(experienceToAdd, resume)
  }

  fun addSideProjectToResume(resumeId: Long, experience: ExperienceDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resume.skills
    val sideProjectToAdd = resumeMapper.mapExperienceDTOToResume(experience, resumeSkills)
    return sideProjectService.addSideProjectToResume(sideProjectToAdd, resume)
  }

  fun editExperienceInResume(resumeId: Long, experienceId: Long, experience: ExperienceDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resume.skills
    val experienceToEdit = resumeMapper.mapExperienceDTOToResume(experience, resumeSkills).copy(id = experienceId)
    return experienceService.editResume(experienceToEdit, resume)
  }

  fun editSideProjectInResume(resumeId: Long, sideProjectId: Long, experience: ExperienceDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val resumeSkills = resume.skills
    val sideProjectToEdit = resumeMapper.mapExperienceDTOToResume(experience, resumeSkills).copy(id = sideProjectId)
    return sideProjectService.editResume(sideProjectToEdit, resume)
  }

  fun deleteExperienceFromResume(resumeId: Long, experienceId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    return experienceService.deleteExperienceFromResume(experienceId, resume)
  }

  fun deleteSideProjectFromResume(resumeId: Long, sideProjectId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    return sideProjectService.deleteExperienceFromResume(sideProjectId, resume)
  }

  fun addHobbyToResume(resumeId: Long, hobbyName: String): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val hobby = Hobby(hobbyName)
    return hobbyService.addHobbyToResume(hobby, resume)
  }

  fun deleteHobbyFromResume(resumeId: Long, hobbyName: String): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val hobby = Hobby(hobbyName)
    return hobbyService.deleteHobbyFromResume(hobby, resume)
  }

  fun addLanguageToResume(resumeId: Long, language: LanguageDTO): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val language = Language(
      null,
      language.name,
      LanguageLevel.entries.first { it.name == language.level }
    )
    return languageService.addLanguageToResume(resume, language)
  }

  fun editLanguageInResume(resumeId: Long, language: LanguageDTO, languageId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val language = Language(
      languageId,
      language.name,
      LanguageLevel.entries.first { it.name == language.level }
    )
    return languageService.editLanguageInResume(resume, language)
  }

  fun deleteLanguageFromResume(resumeId: Long, languageId: Long): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val languageToDelete = resume.languages.find { it.id == languageId } ?: throw LanguageNotFound("id: $languageId")
    return languageService.deleteLanguageFromResume(resume, languageToDelete)
  }

}