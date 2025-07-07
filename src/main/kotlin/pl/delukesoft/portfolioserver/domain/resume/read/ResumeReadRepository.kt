package pl.delukesoft.portfolioserver.domain.resume.read

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import pl.delukesoft.portfolioserver.domain.resume.read.model.Resume

interface ResumeReadRepository: MongoRepository<Resume, Long> {

  fun findFirstByOrderByLastModifiedDesc(): Resume?

  fun findResumeById(id: Long): Resume?

  @Query("{ 'user.id' : ?0, 'user.roles' : { \$in: ?1 } }")
  fun findResumeByUserIdAndRoles(id: Long, roles: List<String>): List<Resume>

  @Query("{ 'user.roles' : { \$in: ?0 } }")
  fun findResumesByRoles(roles: List<String>): List<Resume>

  @Query("{ 'id' : ?0, 'user.id' : ?1 }")
  fun findResumeByIdAndUserId(id: Long, userId: Long): Resume?

}