package pl.delukesoft.portfolioserver.domain.resume.skill.domain

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.exception.SkillDomainNotFound
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class SkillDomainService(
  private val skillDomainRepository: SkillDomainRepository,
  private val generatorService: GeneratorService
) {

  fun findByName(name: String): SkillDomain {
    return skillDomainRepository.findSkillDomainByName(name) ?: throw SkillDomainNotFound(name)
  }

  fun saveAll(skills: List<SkillDomain>): List<SkillDomain> {
    return skills.map { save(it) }
  }

  fun save(skillDomain: SkillDomain): SkillDomain {
    if (skillDomain.id == null && !skillDomainRepository.existsSkillDomainsByName(skillDomain.name)) {
      val generatedId = generatorService.getAndIncrement("skill_domain")
      return skillDomainRepository.save(skillDomain.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

}