package pl.delukesoft.portfolioserver.portfolio.domain

import org.springframework.data.mongodb.repository.MongoRepository
import pl.delukesoft.portfolioserver.portfolio.domain.model.Resume

interface ResumeRepository: MongoRepository<Resume, Long> {

  fun findFirstByOrderByLastModifiedDesc(): Resume?

  fun findResumeById(id: Long): Resume?

}