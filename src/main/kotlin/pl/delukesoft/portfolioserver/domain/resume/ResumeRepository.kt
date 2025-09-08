package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ResumeRepository: MongoRepository<Resume, Long> {

  @Query("{ 'id' : ?0, 'shortcut.user.username' : ?1}")
  fun findResumeByIdAndUsername(id: Long, username: String): Resume?

  fun findResumeById(id: Long): Resume?

}