package pl.delukesoft.portfolioserver.portfolio

import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.CurriculumNotFound
import pl.delukesoft.portfolioserver.portfolio.model.Resume

@Service
class ResumeService(
  private val resumeRepository: ResumeRepository,
) {
  private val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)

  fun getCvById(id: Long): Resume {
    log.info("Getting CV with id: $id")
    return resumeRepository.findResumeById(id) ?: throw CurriculumNotFound()
  }

  fun getDefaultCV(): Resume {
    log.info("Getting default CV")
    return resumeRepository.findFirstByOrderByLastModifiedDesc() ?: throw CurriculumNotFound()
  }

}