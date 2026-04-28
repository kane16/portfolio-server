package pl.delukesoft.portfolioserver.resume.education

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.resume.history.ResumeVersion
import pl.delukesoft.portfolioserver.platform.sequence.GeneratorService

@Service
class EducationService(
  private val resumeModifyRepository: ResumeModifyRepository,
  private val generatorService: GeneratorService
) {

  fun addEducationToResume(educationEntry: Education, resumeVersion: ResumeVersion): Boolean {
    val education = resumeVersion.resume.education + educationEntry.copy(
      id = generatorService.getAndIncrement("education")
    )

    return resumeModifyRepository.changeEducationInResume(education, resumeVersion)
  }

  fun modifyEducationInResume(educationEntry: Education, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val education = resume.education.map {
      if (it.id == educationEntry.id) educationEntry else it
    }

    return resumeModifyRepository.changeEducationInResume(education, resumeVersion)
  }

  fun deleteEducationFromResume(educationId: Long, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val education = resume.education.filter { it.id != educationId }

    return resumeModifyRepository.changeEducationInResume(education, resumeVersion)
  }


}