package pl.delukesoft.portfolioserver.domain.resume.experience

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeRepository
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService
import pl.delukesoft.portfolioserver.domain.validation.ValidateExperiences
import pl.delukesoft.portfolioserver.domain.validation.Validator

@Service
class ExperienceService(
  private val resumeRepository: ResumeRepository,
  private val generatorService: GeneratorService,
  @Qualifier("jobExperienceValidator") val validator: Validator<Experience>,
) {

  fun addExperienceToResume(experienceToAdd: Experience, resume: Resume): Boolean {
    val experienceToSave = experienceToAdd.copy(
      id = generatorService.getAndIncrement("experience")
    )
    val experiences: List<Experience> = (resume.experience + experienceToSave).sortedBy { it.timeframe.start }
    return changeExperiencesInResume(experiences, resume)
  }

  fun editResume(experienceToEdit: Experience, resume: Resume): Boolean {
    val experiences: List<Experience> = resume.experience.map {
      if (it.id == experienceToEdit.id) experienceToEdit else it
    }.sortedBy { it.timeframe.start }
    return changeExperiencesInResume(experiences, resume)
  }

  fun deleteExperienceFromResume(experienceId: Long, resume: Resume): Boolean {
    val experiences: List<Experience> = resume.experience.filter { it.id != experienceId }
    return changeExperiencesInResume(experiences, resume)
  }

  @ValidateExperiences
  fun changeExperiencesInResume(experiences: List<Experience>, resume: Resume): Boolean {
    return resumeRepository.changeExperienceToResume(resume.id!!, experiences) > 0
  }


}