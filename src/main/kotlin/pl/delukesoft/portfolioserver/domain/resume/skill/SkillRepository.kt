package pl.delukesoft.portfolioserver.domain.resume.skill

import org.springframework.data.mongodb.repository.MongoRepository

interface SkillRepository: MongoRepository<Skill, Long> {

  fun findByName(name: String): Skill?
  fun findByUsername(username: String): List<Skill>
  fun findByNameAndUsername(name: String, username: String): Skill?
  fun existsByNameAndUsername(name: String, username: String): Boolean

}