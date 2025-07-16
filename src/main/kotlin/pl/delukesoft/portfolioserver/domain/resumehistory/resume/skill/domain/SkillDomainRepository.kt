package pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface SkillDomainRepository: MongoRepository<SkillDomain, Long> {

  fun existsSkillDomainsByName(name: String): Boolean
  fun findSkillDomainByName(name: String): SkillDomain?

}