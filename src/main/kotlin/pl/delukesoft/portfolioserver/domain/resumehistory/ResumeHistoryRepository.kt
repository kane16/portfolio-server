package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update

interface ResumeHistoryRepository : MongoRepository<ResumeHistory, Long> {

  @Query("{ 'user.username' : ?0, 'user.roles' : { \$in: ?1 } }")
  fun findResumeHistoryByUsernameAndRoles(username: String, roles: List<String>): ResumeHistory?

  @Query("{ 'user.roles' : { \$in: ?0 } }")
  fun findResumesHistoryByRoles(roles: List<String>): List<ResumeHistory>

  @Query("{ 'id' : ?0, 'user.username' : ?1 }")
  fun findResumeHistoryByIdAndUsername(id: Long, username: String): ResumeHistory?

  @Query("{ 'user.roles' : { \$in: ?0 } }")
  fun findResumeHistoryByRoles(listOf: List<String>): List<ResumeHistory>

  @Query("{ 'user.username' : ?0 }")
  fun findResumeHistoryByUsername(username: String): ResumeHistory?

  fun existsByUser_Username(username: String): Boolean

  @Query("{ 'user.username' : ?0 }")
  @Aggregation("{ \$max: 'versions.version' }")
  fun findMaxVersionInResumeHistoryByUsername(username: String): Long

  @Query("{ 'user.username' : ?0 }")
  @Update("{ \$addToSet: { 'versions': ?1 } }")
  fun addResumeHistoryVersionByUsername(username: String, resumeVersion: ResumeVersion): Long

}