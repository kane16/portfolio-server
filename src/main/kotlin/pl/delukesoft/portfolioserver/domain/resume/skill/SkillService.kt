package pl.delukesoft.portfolioserver.domain.resume.skill

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeModifyRepository

@Service
class SkillService(
  private val resumeModifyRepository: ResumeModifyRepository
) {

  fun addSkillToResume(resume: Resume, skillToAdd: Skill): Boolean {
    val skills = resume.skills + skillToAdd
    return resumeModifyRepository.changeSkillsInResume(skills, resume)
  }

  fun deleteSkillFromResume(resume: Resume, skillToRemove: Skill): Boolean {
    val skills = resume.skills.filter { it.name != skillToRemove.name }
    return resumeModifyRepository.changeSkillsInResume(skills, resume)
  }

  fun editSkill(resume: Resume, skillToEdit: Skill, skillUpdate: Skill): Boolean {
    val skills = resume.skills.map {
      if (it.name == skillToEdit.name) skillUpdate else it
    }
    return resumeModifyRepository.changeSkillsInResume(skills, resume)

  }

}