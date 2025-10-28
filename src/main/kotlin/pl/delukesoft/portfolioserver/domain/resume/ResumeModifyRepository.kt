package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.education.Education
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.validation.*

@Component
class ResumeModifyRepository(
  private val resumeRepository: ResumeRepository
) {

  @ResumeModification
  @ValidateShortcut
  fun changeShortcutInResume(shortcut: ResumeShortcut, resume: Resume): Boolean {
    return resumeRepository.changeShortcutInResume(resume.id!!, shortcut) > 0
  }

  @ResumeModification
  @ValidateExperiences
  fun changeExperiencesInResume(experiences: List<Experience>, resume: Resume): Boolean {
    return resumeRepository.changeExperienceToResume(resume.id!!, experiences) > 0
  }

  @ResumeModification
  @ValidateHobbies
  fun changeHobbiesInResume(hobbies: List<Hobby>, resume: Resume): Boolean {
    return resumeRepository.changeHobbiesInResume(resume.id!!, hobbies) > 0
  }

  @ResumeModification
  @ValidateLanguages
  fun changeLanguagesInResume(languages: List<Language>, resume: Resume): Boolean {
    return resumeRepository.changeLanguagesInResume(resume.id!!, languages) > 0
  }

  @ResumeModification
  @ValidateExperiences
  fun changeSideProjectsInResume(sideProjects: List<Experience>, resume: Resume): Boolean {
    return resumeRepository.changeSideProjectsInResume(resume.id!!, sideProjects) > 0
  }

  @ResumeModification
  @ValidateSkill
  fun changeSkillsInResume(skills: List<Skill>, resume: Resume): Boolean {
    return resumeRepository.changeSkillsInResume(resume.id!!, skills) > 0
  }

  @ResumeModification
  @ValidateEducation
  fun changeEducationInResume(education: List<Education>, resume: Resume): Boolean {
    return resumeRepository.changeEducationInResume(resume.id!!, education) > 0
  }

}