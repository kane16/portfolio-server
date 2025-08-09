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

  @Query("{ 'user.roles' : { \$in: ?0 } }")
  fun findResumeHistoryByRoles(listOf: List<String>): List<ResumeHistory>

  @Query("{ 'user.username' : ?0 }")
  fun findResumeHistoryByUsername(username: String): ResumeHistory?

  fun existsByUser_Username(username: String): Boolean

  @Aggregation(
    pipeline = [
      "{ \$match: { 'user.username' : ?0} }",
      "{ \$lookup: {from:  'ResumeVersion', localField: 'versions.\$id', foreignField: '_id', as: 'lookup_versions'} }",
      "{ \$unwind: {path: '\$lookup_versions'} }",
      "{ \$group:  { _id: '\$lookup_versions.version', maxVersion: { \$max: '\$lookup_versions.version' } } }",
      "{ \$project:  { _id : '\$maxVersion' }}"
    ]
  )
  fun findMaxVersionInResumeHistoryByUsername(username: String): Long

  @Query("{ 'user.username' : ?0 }")
  @Update("{ \$addToSet: { 'versions': ?1 } }")
  fun addResumeHistoryVersionByUsername(username: String, resumeVersion: ResumeVersion): Long

}