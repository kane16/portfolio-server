package pl.delukesoft.portfolioserver.domain.resume.experience

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeRepository
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class ExperienceService(
  private val resumeRepository: ResumeRepository,
  private val generatorService: GeneratorService
) {

  fun addExperienceToResume(experienceToAdd: Experience, resume: Resume): Boolean {
    val experienceToSave = experienceToAdd.copy(
      id = generatorService.getAndIncrement("experience")
    )
    return resumeRepository.addExperienceToResume(resume.id!!, experienceToSave) > 0
  }

  fun editResume(experienceToEdit: Experience, resume: Resume): Boolean {
    return resumeRepository.editExperienceInResume(resume.id!!, experienceToEdit.id!!, experienceToEdit) > 0
  }

  fun deleteExperienceFromResume(experienceId: Long, resume: Resume): Boolean {
    return resumeRepository.deleteExperienceFromResume(resume.id!!, experienceId) > 0
  }


}