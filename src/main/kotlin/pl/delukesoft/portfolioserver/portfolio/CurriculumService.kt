package pl.delukesoft.portfolioserver.portfolio

import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import pl.delukesoft.blog.image.exception.CurriculumNotFound
import pl.delukesoft.portfolioserver.portfolio.model.Curriculum

@Service
class CurriculumService(
  private val curriculumRepository: CurriculumRepository,
) {

  fun getCvById(id: Long): Curriculum {
    return curriculumRepository.findCurriculumById(id) ?: throw CurriculumNotFound()
  }

  fun getDefaultCV(): Curriculum {
    return curriculumRepository.findFirstByOrderByLastModifiedDesc() ?: throw CurriculumNotFound()
  }

}