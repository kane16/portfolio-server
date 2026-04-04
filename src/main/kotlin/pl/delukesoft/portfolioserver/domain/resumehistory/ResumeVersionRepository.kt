package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import pl.delukesoft.portfolioserver.domain.resume.education.Education
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import java.time.LocalDateTime

interface ResumeVersionRepository : MongoRepository<ResumeVersion, Long> {

  @Query("{ 'id' : ?0 }")
  @Update("{ '\$set': { 'state' : ?1 } }")
  fun changeResumeStatus(id: Long, status: ResumeVersionState): Long

  @Query("{ 'id' : ?0 }")
  fun findVersionById(resumeId: Long): ResumeVersion?

  @Query("{ 'id' : ?0, 'resume.shortcut.user.username' : ?1}")
  fun findResumeByIdAndUsername(id: Long, username: String): ResumeVersion?

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'resume.skills': ?1 } }")
  fun changeSkillsInResume(id: Long, skills: List<Skill>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'resume.experience': ?1 } }")
  fun changeExperienceToResume(id: Long, experiences: List<Experience>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'resume.lastModified': ?1 } }")
  fun updateLastModified(id: Long, lastModified: LocalDateTime): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'resume.hobbies': ?1 } }")
  fun changeHobbiesInResume(id: Long, hobbies: List<Hobby>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'resume.languages': ?1 } }")
  fun changeLanguagesInResume(id: Long, languages: List<Language>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'resume.sideProjects': ?1 } }")
  fun changeSideProjectsInResume(id: Long, sideProjects: List<Experience>): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'resume.shortcut': ?1 } }")
  fun changeShortcutInResume(id: Long, shortcut: ResumeShortcut): Long

  @Query("{ 'id' : ?0 }")
  @Update("{ \$set: { 'resume.education': ?1 } }")
  fun changeEducationInResume(id: Long, education: List<Education>): Long

}