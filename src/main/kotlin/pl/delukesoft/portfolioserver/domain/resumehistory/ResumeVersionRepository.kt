package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update

interface ResumeVersionRepository : MongoRepository<ResumeVersion, Long> {

  @Query("{ '_id' : ?0 }")
  @Update("{ '\$set': { 'state' : ?1 } }")
  fun changeResumeStatus(id: Long, status: ResumeVersionState): Long

  @Query("{ 'resumeId' : ?0 }")
  fun findResumeVersionsByResumeId(resumeId: Long): ResumeVersion?

}