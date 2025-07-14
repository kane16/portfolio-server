package pl.delukesoft.portfolioserver.domain.resume.read

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import pl.delukesoft.portfolioserver.domain.resume.model.Resume

interface ResumeReadRepository: MongoRepository<Resume, Long> {

  fun findFirstByOrderByLastModifiedDesc(): Resume?

  fun findResumeById(id: Long): Resume?

  @Query("{ 'user.username' : ?0, 'user.roles' : { \$in: ?1 } }")
  fun findResumeByUsernameAndRoles(username: String, roles: List<String>): List<Resume>

  @Query("{ 'user.roles' : { \$in: ?0 } }")
  fun findResumesByRoles(roles: List<String>): List<Resume>

  @Query("{ 'id' : ?0, 'user.username' : ?1 }")
  fun findResumeByIdAndUsername(id: Long, username: String): Resume?

}