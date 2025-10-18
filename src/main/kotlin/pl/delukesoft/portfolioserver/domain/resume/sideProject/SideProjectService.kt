package pl.delukesoft.portfolioserver.domain.resume.sideProject

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class SideProjectService(
  private val resumeModificationRepository: ResumeModifyRepository,
  private val generatorService: GeneratorService,
) {

  fun addSideProjectToResume(sideProjectToAdd: Experience, resume: Resume): Boolean {
    val sideProjectToSave = sideProjectToAdd.copy(
      id = generatorService.getAndIncrement("sideProject")
    )
    val sideProjects = (resume.sideProjects + sideProjectToSave).sortedBy { it.timeframe.start }
    return resumeModificationRepository.changeSideProjectsInResume(sideProjects, resume)
  }

  fun editResume(sideProjectToEdit: Experience, resume: Resume): Boolean {
    val sideProjects = resume.sideProjects.map {
      if (it.id == sideProjectToEdit.id) sideProjectToEdit else it
    }.sortedBy { it.timeframe.start }
    return resumeModificationRepository.changeSideProjectsInResume(sideProjects, resume)
  }

  fun deleteExperienceFromResume(sideProjectId: Long, resume: Resume): Boolean {
    val sideProjects = resume.sideProjects.filter { it.id != sideProjectId }
    return resumeModificationRepository.changeSideProjectsInResume(sideProjects, resume)
  }


}