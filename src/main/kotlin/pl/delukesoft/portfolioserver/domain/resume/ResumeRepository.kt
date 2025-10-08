package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill

interface ResumeRepository : MongoRepository<Resume, Long> {

  @Query("{ 'id' : ?0, 'shortcut.user.username' : ?1}")
  fun findResumeByIdAndUsername(id: Long, username: String): Resume?

  fun findResumeById(id: Long): Resume?

  @Query("{ 'id' : ?0 }")
  @Update("{ \$addToSet: { 'skills': ?1 } }")
  fun addSkillToResume(id: Long, skill: Skill): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$pull: { 'skills': ?1 } }")
  fun deleteSkillFromResume(id: Long, skillToRemove: Skill): Long

  @Query("{ 'id' : ?0, 'skills': { '\$elemMatch': { 'name': ?1 } } }")
  @Update("{ '\$set': { 'skills.$': ?2 } }")
  fun updateSkill(id: Long, skillName: String, skillUpdate: Skill): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$addToSet: { 'experience': ?1 } }")
  fun addExperienceToResume(id: Long, experienceToAdd: Experience): Long

}