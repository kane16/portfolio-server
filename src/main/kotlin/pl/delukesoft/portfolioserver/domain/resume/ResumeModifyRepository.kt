package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.validation.ValidateExperiences
import pl.delukesoft.portfolioserver.domain.validation.ValidateHobbies
import pl.delukesoft.portfolioserver.domain.validation.ValidateLanguages

@Component
class ResumeModifyRepository(
  private val resumeRepository: ResumeRepository
) {

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




}