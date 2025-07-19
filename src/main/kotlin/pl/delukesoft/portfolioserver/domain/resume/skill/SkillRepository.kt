package pl.delukesoft.portfolioserver.domain.resume.skill

import org.springframework.data.mongodb.repository.MongoRepository

interface SkillRepository: MongoRepository<Skill, Long> {

  fun findByName(name: String): Skill?
  fun existsByName(name: String): Boolean
}