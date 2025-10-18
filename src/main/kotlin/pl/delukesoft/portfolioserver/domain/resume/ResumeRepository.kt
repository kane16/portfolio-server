package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import java.time.LocalDateTime

interface ResumeRepository : MongoRepository<Resume, Long> {

  @Query("{ 'id' : ?0, 'shortcut.user.username' : ?1}")
  fun findResumeByIdAndUsername(id: Long, username: String): Resume?

  fun findResumeById(id: Long): Resume?

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'skills': ?1 } }")
  fun changeSkillsInResume(id: Long, skills: List<Skill>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'experience': ?1 } }")
  fun changeExperienceToResume(id: Long, experiences: List<Experience>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'lastModified': ?1 } }")
  fun updateLastModified(id: Long, lastModified: LocalDateTime): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'hobbies': ?1 } }")
  fun changeHobbiesInResume(id: Long, hobbies: List<Hobby>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'languages': ?1 } }")
  fun changeLanguagesInResume(id: Long, languages: List<Language>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'sideProjects': ?1 } }")
  fun changeSideProjectsInResume(id: Long, sideProjects: List<Experience>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'shortcut': ?1 } }")
  fun changeShortcutInResume(id: Long, shortcut: ResumeShortcut): Long

}