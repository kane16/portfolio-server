package pl.delukesoft.portfolioserver.domain.resume.skill

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion

@Service
class SkillService(
  private val resumeModifyRepository: ResumeModifyRepository
) {

  fun addSkillToResume(resumeVersion: ResumeVersion, skillToAdd: Skill): Boolean {
    val resume = resumeVersion.resume
    val skills = resume.skills + skillToAdd
    return resumeModifyRepository.changeSkillsInResume(skills, resumeVersion)
  }

  fun deleteSkillFromResume(resumeVersion: ResumeVersion, skillToRemove: Skill): Boolean {
    val resume = resumeVersion.resume
    val skills = resume.skills.filter { it.name != skillToRemove.name }
    return resumeModifyRepository.changeSkillsInResume(skills, resumeVersion)
  }

  fun editSkill(resumeVersion: ResumeVersion, skillToEdit: Skill, skillUpdate: Skill): Boolean {
    val resume = resumeVersion.resume
    val skills = resume.skills.map {
      if (it.name == skillToEdit.name) skillUpdate else it
    }
    return resumeModifyRepository.changeSkillsInResume(skills, resumeVersion)

  }

}