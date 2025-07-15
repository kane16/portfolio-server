package pl.delukesoft.portfolioserver.domain.resumehistory.resume

import org.springframework.data.mongodb.repository.MongoRepository

interface ResumeRepository: MongoRepository<Resume, Long> {

  fun findFirstByOrderByLastModifiedDesc(): Resume?

  fun findResumeById(id: Long): Resume?

}