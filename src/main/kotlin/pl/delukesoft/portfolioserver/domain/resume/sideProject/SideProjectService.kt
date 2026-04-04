package pl.delukesoft.portfolioserver.domain.resume.sideProject

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class SideProjectService(
  private val resumeModificationRepository: ResumeModifyRepository,
  private val generatorService: GeneratorService,
) {

  fun addSideProjectToResume(sideProjectToAdd: Experience, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val sideProjectToSave = sideProjectToAdd.copy(
      id = generatorService.getAndIncrement("sideProject")
    )
    val sideProjects = (resume.sideProjects + sideProjectToSave).sortedBy { it.timeframe.start }
    return resumeModificationRepository.changeSideProjectsInResume(sideProjects, resumeVersion)
  }

  fun editResume(sideProjectToEdit: Experience, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val sideProjects = resume.sideProjects.map {
      if (it.id == sideProjectToEdit.id) sideProjectToEdit else it
    }.sortedBy { it.timeframe.start }
    return resumeModificationRepository.changeSideProjectsInResume(sideProjects, resumeVersion)
  }

  fun deleteExperienceFromResume(sideProjectId: Long, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val sideProjects = resume.sideProjects.filter { it.id != sideProjectId }
    return resumeModificationRepository.changeSideProjectsInResume(sideProjects, resumeVersion)
  }


}