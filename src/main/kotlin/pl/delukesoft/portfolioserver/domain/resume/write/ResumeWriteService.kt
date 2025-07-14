package pl.delukesoft.portfolioserver.domain.resume.write

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.model.Resume
import pl.delukesoft.portfolioserver.domain.resume.print.GeneratorService

@Service
class ResumeWriteService(
  val resumeWriteRepository: ResumeWriteRepository,
  val generatorService: GeneratorService,
) {

  fun saveAll(resumes: List<Resume>): List<Resume> =
    resumes.map { save(it) }

  fun save(resume: Resume): Resume {
    if (resume.id == null) {
      val generatedId = generatorService.getAndIncrement("resume")
      return resumeWriteRepository.save(resume.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

}