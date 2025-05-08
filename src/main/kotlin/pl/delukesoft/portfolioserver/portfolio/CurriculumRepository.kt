package pl.delukesoft.portfolioserver.portfolio

import org.springframework.data.mongodb.repository.MongoRepository
import pl.delukesoft.portfolioserver.portfolio.model.Curriculum

interface CurriculumRepository: MongoRepository<Curriculum, Long> {

  fun findFirstByOrderByLastModifiedDesc(): Curriculum?

  fun findCurriculumById(id: Long): Curriculum?

}