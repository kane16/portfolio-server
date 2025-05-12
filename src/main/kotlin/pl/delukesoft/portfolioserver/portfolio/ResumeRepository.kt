package pl.delukesoft.portfolioserver.portfolio

import org.springframework.data.mongodb.repository.MongoRepository
import pl.delukesoft.portfolioserver.portfolio.model.Resume

interface ResumeRepository: MongoRepository<Resume, Long> {

  fun findFirstByOrderByLastModifiedDesc(): Resume?

  fun findResumeById(id: Long): Resume?

}