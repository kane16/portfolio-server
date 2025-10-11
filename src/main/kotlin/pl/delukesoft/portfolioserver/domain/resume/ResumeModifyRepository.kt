package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.validation.ValidateExperiences

@Component
class ResumeModifyRepository(
  private val resumeRepository: ResumeRepository
) {

  @ResumeModification
  @ValidateExperiences
  fun changeExperiencesInResume(experiences: List<Experience>, resume: Resume): Boolean {
    return resumeRepository.changeExperienceToResume(resume.id!!, experiences) > 0
  }


}