package pl.delukesoft.portfolioserver.domain.author

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain

interface AuthorRepository : MongoRepository<Author, Long> {

  fun findByRolesContains(role: String): Author?
  fun findByUsername(username: String): Author?

  @Query("{'username' : ?0}")
  @Update("{ \$addToSet: { 'skills': ?1 } }")
  fun addSkillToAuthor(username: String, skill: Skill): Int

  @Query("{'username' : ?0}")
  @Update("{ \$addToSet: { 'domains': ?1 } }")
  fun addDomainToAuthor(username: String, domain: SkillDomain): Int

  @Query("{'username' : ?0}")
  @Update("{ \$addToSet: { 'businesses': ?1 } }")
  fun addBusinessesToAuthor(username: String, business: Business): Int

}