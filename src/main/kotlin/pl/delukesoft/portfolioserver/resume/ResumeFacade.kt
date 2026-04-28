package pl.delukesoft.portfolioserver.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.resume.exception.ResumeNotFound
import pl.delukesoft.portfolioserver.resume.exception.ResumeOperationNotAllowed
import pl.delukesoft.portfolioserver.security.UserContext
import pl.delukesoft.portfolioserver.resume.history.ResumeHistoryDTO
import pl.delukesoft.portfolioserver.resume.shortcut.ResumeShortcutDTO
import pl.delukesoft.portfolioserver.resume.ResumeEditDTO
import pl.delukesoft.portfolioserver.resume.skill.SkillDTO
import pl.delukesoft.portfolioserver.resume.skill.SkillMapper
import pl.delukesoft.portfolioserver.resume.ResumeService
import pl.delukesoft.portfolioserver.resume.skill.SkillService
import pl.delukesoft.portfolioserver.resume.skill.exception.SkillNotFound
import pl.delukesoft.portfolioserver.resume.history.ResumeHistoryService
import pl.delukesoft.portfolioserver.resume.history.ResumeVersion

@Component
class ResumeFacade(
  private val resumeService: ResumeService,
  private val resumeHistoryService: ResumeHistoryService,
  private val resumeMapper: ResumeMapper,
  private val userContext: UserContext,
  private val skillMapper: SkillMapper,
  private val skillService: SkillService
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  private val currentAuthor
    get() = requireNotNull(userContext.author) { "Authenticated author is required" }

  fun getEditDTOById(id: Long): ResumeEditDTO {
    return resumeMapper.mapResumeToEditDTO(resumeService.getResumeById(id, userContext.user))
  }

  fun getById(id: Long): ResumeVersion {
    return resumeService.getResumeById(id, userContext.user)
  }

  fun getDefaultCV(): ResumeVersion {
    val defaultResume = resumeService.getDefaultCV(userContext.user)
    return defaultResume
  }

  fun getUserHistory(): ResumeHistoryDTO {
    val history = resumeHistoryService.findByUsername(currentUser.username)
    return resumeMapper.mapHistoryToDTO(history)
  }

  fun initiateResume(shortcutDTO: ResumeShortcutDTO): ResumeEditDTO {
    val shortcut = resumeMapper.mapShortcutDTOToResume(shortcutDTO, currentUser)
    val resume = resumeService.addResume(shortcut)
    return resumeMapper.mapResumeToEditDTO(resume)
  }

  fun unpublishResume(): ResumeEditDTO {
    val publishedVersion = resumeHistoryService.findPublishedResumeVersion(currentUser.username)
    if (publishedVersion?.version == null) {
      throw ResumeOperationNotAllowed("No version has been published yet")
    }
    resumeService.unpublishResume(publishedVersion, userContext.user?.username!!)
    return resumeMapper.mapResumeToEditDTO(
      resumeService.getResumeById(publishedVersion.id!!, userContext.user)
    )
  }

  fun editResumeShortcut(id: Long, shortcutDTO: ResumeShortcutDTO): ResumeEditDTO {
    val resume = resumeService.getResumeById(id, currentUser)
    val shortcut = resumeMapper.mapShortcutDTOToResume(shortcutDTO, currentUser)
    return resumeMapper.mapResumeToEditDTO(resumeService.editResumeShortcut(resume, shortcut))
  }

  fun publishResume(version: Long): ResumeEditDTO {
    val publishedVersion = resumeHistoryService.findPublishedResumeVersion(currentUser.username)
    val versionToPublish = resumeHistoryService.findVersionByIdAndUsername(version, currentUser.username)
    if (publishedVersion != null) {
      throw ResumeOperationNotAllowed("Published version already exists")
    }
    if (versionToPublish == null) {
      throw ResumeNotFound()
    }
    resumeService.publishResume(versionToPublish, userContext.user?.username!!)
    return resumeMapper.mapResumeToEditDTO(
      resumeService.getResumeById(versionToPublish.id!!, userContext.user)
    )
  }


  fun addSkillToResume(id: Long, skill: SkillDTO): Boolean {
    val skillToAdd = skillMapper.mapToSkill(skill, currentAuthor.domains)
    val resumeToModify = resumeService.getResumeById(id, currentUser)
    return skillService.addSkillToResume(resumeToModify, skillToAdd)
  }

  fun findSkillsByResumeId(resumeId: Long): List<SkillDTO> {
    val resumeVersion = resumeService.getResumeById(resumeId, userContext.user)
    val resume = resumeVersion.resume
    return resume.skills.map { skillMapper.mapToDTO(it) }
  }

  fun deleteSkillFromResume(resumeId: Long, skillNameToRemove: String): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, userContext.user)
    val resume = resumeVersion.resume
    val skillToRemove = resume.skills.find { it.name == skillNameToRemove } ?: throw SkillNotFound(skillNameToRemove)
    return skillService.deleteSkillFromResume(resumeVersion, skillToRemove)
  }

  fun editSkillWithName(resumeId: Long, skillName: String, skill: SkillDTO): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, userContext.user)
    val resume = resumeVersion.resume
    val skillToEdit = resume.skills.find { it.name == skillName } ?: throw SkillNotFound(skillName)
    val skillUpdate = skillMapper.mapToSkill(skill, currentAuthor.domains)
    return skillService.editSkill(resumeVersion, skillToEdit, skillUpdate)
  }

}