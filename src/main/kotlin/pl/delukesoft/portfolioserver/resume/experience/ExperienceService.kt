package pl.delukesoft.portfolioserver.resume.experience

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.resume.history.ResumeVersion
import pl.delukesoft.portfolioserver.platform.sequence.GeneratorService
import pl.delukesoft.portfolioserver.resume.validation.Validator

@Service
class ExperienceService(
  private val resumeModifyRepository: ResumeModifyRepository,
  private val generatorService: GeneratorService,
  @Qualifier("jobExperienceValidator") val validator: Validator<Experience>,
) {

  fun addExperienceToResume(experienceToAdd: Experience, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val experienceToSave = experienceToAdd.copy(
      id = generatorService.getAndIncrement("experience")
    )
    val experiences: List<Experience> = (resume.experience + experienceToSave).sortedBy { it.timeframe.start }
    return resumeModifyRepository.changeExperiencesInResume(experiences, resumeVersion)
  }

  fun editResume(experienceToEdit: Experience, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val experiences: List<Experience> = resume.experience.map {
      if (it.id == experienceToEdit.id) experienceToEdit else it
    }.sortedBy { it.timeframe.start }
    return resumeModifyRepository.changeExperiencesInResume(experiences, resumeVersion)
  }

  fun deleteExperienceFromResume(experienceId: Long, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val experiences: List<Experience> = resume.experience.filter { it.id != experienceId }
    return resumeModifyRepository.changeExperiencesInResume(experiences, resumeVersion)
  }


}