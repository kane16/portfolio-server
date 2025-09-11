package pl.delukesoft.portfolioserver.domain.resume.skill.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.exception.SkillDomainNotFound
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService
import pl.delukesoft.portfolioserver.domain.validation.ValidateDomain

@Service
class SkillDomainService(
  private val skillDomainRepository: SkillDomainRepository,
  private val generatorService: GeneratorService
) {

  private val log = LoggerFactory.getLogger(this::class.java)

  fun getByName(name: String): SkillDomain {
    return skillDomainRepository.findSkillDomainByName(name) ?: throw SkillDomainNotFound(name)
  }

  fun addDomain(domainToAdd: SkillDomain): SkillDomain {
    return save(domainToAdd)
  }

  @ValidateDomain
  fun save(skillDomain: SkillDomain): SkillDomain {
    if (skillDomain.id == null && !skillDomainRepository.existsSkillDomainsByName(skillDomain.name)) {
      val generatedId = generatorService.getAndIncrement("skill_domain")
      return skillDomainRepository.save(skillDomain.copy(id = generatedId))
    }
    return skillDomainRepository.findSkillDomainByName(skillDomain.name) ?: throw SkillDomainNotFound(skillDomain.name)
  }

  fun getUserDomains(username: String): List<SkillDomain> {
    log.info("Getting user domains for user {}", username)
    return skillDomainRepository.findSkillDomainsByUsername(username)
  }

  fun getAll(): List<SkillDomain> {
    return skillDomainRepository.findAll()
  }

}