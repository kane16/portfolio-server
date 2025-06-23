package pl.delukesoft.portfolioserver.domain.resume.read

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.CurriculumNotFound
import pl.delukesoft.portfolioserver.domain.resume.read.model.Resume

@Service
class ResumeResumeService(
  private val resumeReadRepository: ResumeReadRepository,
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  fun getCvById(id: Long): Resume {
    log.info("Getting CV with id: $id")
    return resumeReadRepository.findResumeById(id) ?: throw CurriculumNotFound()
  }

  fun getDefaultCV(): Resume {
    log.info("Getting default CV")
    return resumeReadRepository.findFirstByOrderByLastModifiedDesc() ?: throw CurriculumNotFound()
  }

}