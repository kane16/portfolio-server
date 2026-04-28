package pl.delukesoft.portfolioserver.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.resume.education.Education
import pl.delukesoft.portfolioserver.resume.experience.Experience
import pl.delukesoft.portfolioserver.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.resume.language.Language
import pl.delukesoft.portfolioserver.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.resume.skill.Skill
import pl.delukesoft.portfolioserver.resume.history.ResumeVersion
import pl.delukesoft.portfolioserver.resume.history.ResumeVersionRepository
import pl.delukesoft.portfolioserver.resume.validation.*

@Component
class ResumeModifyRepository(
  private val resumeVersionRepository: ResumeVersionRepository
) {

  @ResumeModification
  @ValidateShortcut
  fun changeShortcutInResume(shortcut: ResumeShortcut, resumeVersion: ResumeVersion): Boolean {
    return resumeVersionRepository.changeShortcutInResume(resumeVersion.id!!, shortcut) > 0
  }

  @ResumeModification
  @ValidateExperiences
  fun changeExperiencesInResume(experiences: List<Experience>, resumeVersion: ResumeVersion): Boolean {
    return resumeVersionRepository.changeExperienceToResume(resumeVersion.id!!, experiences) > 0
  }

  @ResumeModification
  @ValidateHobbies
  fun changeHobbiesInResume(hobbies: List<Hobby>, resumeVersion: ResumeVersion): Boolean {
    return resumeVersionRepository.changeHobbiesInResume(resumeVersion.id!!, hobbies) > 0
  }

  @ResumeModification
  @ValidateLanguages
  fun changeLanguagesInResume(languages: List<Language>, resumeVersion: ResumeVersion): Boolean {
    return resumeVersionRepository.changeLanguagesInResume(resumeVersion.id!!, languages) > 0
  }

  @ResumeModification
  @ValidateSideProjects
  fun changeSideProjectsInResume(sideProjects: List<Experience>, resumeVersion: ResumeVersion): Boolean {
    return resumeVersionRepository.changeSideProjectsInResume(resumeVersion.id!!, sideProjects) > 0
  }

  @ResumeModification
  @ValidateSkill
  fun changeSkillsInResume(skills: List<Skill>, resumeVersion: ResumeVersion): Boolean {
    return resumeVersionRepository.changeSkillsInResume(resumeVersion.id!!, skills) > 0
  }

  @ResumeModification
  @ValidateEducation
  fun changeEducationInResume(education: List<Education>, resumeVersion: ResumeVersion): Boolean {
    return resumeVersionRepository.changeEducationInResume(resumeVersion.id!!, education) > 0
  }

}