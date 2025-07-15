package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ResumeHistoryRepository : MongoRepository<ResumeHistory, Long> {

  @Query("{ 'user.username' : ?0, 'user.roles' : { \$in: ?1 } }")
  fun findResumeHistoryByUsernameAndRoles(username: String, roles: List<String>): List<ResumeHistory>

  @Query("{ 'user.roles' : { \$in: ?0 } }")
  fun findResumesHistoryByRoles(roles: List<String>): List<ResumeHistory>

  @Query("{ 'id' : ?0, 'user.username' : ?1 }")
  fun findResumeHistoryByIdAndUsername(id: Long, username: String): ResumeHistory?
}