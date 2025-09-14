package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill

interface ResumeRepository : MongoRepository<Resume, Long> {

  @Query("{ 'id' : ?0, 'shortcut.user.username' : ?1}")
  fun findResumeByIdAndUsername(id: Long, username: String): Resume?

  fun findResumeById(id: Long): Resume?

  @Query("{ 'id' : ?0 }")
  @Update("{ \$addToSet: { 'skills': ?1 } }")
  fun addSkillToResume(id: Long, skill: Skill): Long

}