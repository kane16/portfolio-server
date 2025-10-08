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


}