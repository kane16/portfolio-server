package pl.delukesoft.portfolioserver.domain.resume.skill.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface SkillDomainRepository: MongoRepository<SkillDomain, Long> {

  fun existsSkillDomainsByNameAndUsername(name: String, username: String): Boolean
  fun findSkillDomainByName(name: String): SkillDomain?
  fun findSkillDomainsByUsername(username: String): List<SkillDomain>

}