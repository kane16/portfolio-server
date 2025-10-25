package pl.delukesoft.portfolioserver.domain.resume.education

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class EducationService(
  private val resumeModifyRepository: ResumeModifyRepository,
  private val generatorService: GeneratorService
) {

  fun addEducationToResume(educationEntry: Education, resume: Resume): Boolean {
    val education = resume.education + educationEntry.copy(
      id = generatorService.getAndIncrement("education")
    )

    return resumeModifyRepository.changeEducationInResume(education, resume)
  }

  fun modifyEducationInResume(educationEntry: Education, resume: Resume): Boolean {
    val education = resume.education.map {
      if (it.id == educationEntry.id) educationEntry else it
    }

    return resumeModifyRepository.changeEducationInResume(education, resume)
  }

  fun deleteEducationFromResume(educationId: Long, resume: Resume): Boolean {
    val education = resume.education.filter { it.id != educationId }

    return resumeModifyRepository.changeEducationInResume(education, resume)
  }


}