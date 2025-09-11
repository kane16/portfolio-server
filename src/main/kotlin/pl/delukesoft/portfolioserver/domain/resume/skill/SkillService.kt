package pl.delukesoft.portfolioserver.domain.resume.skill

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.skill.exception.SkillNotFound
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService
import pl.delukesoft.portfolioserver.domain.validation.ValidateSkill

@Service
class SkillService(
  private val skillRepository: SkillRepository,
  private val generatorService: GeneratorService
) {

  private val log = LoggerFactory.getLogger(this::class.java)

  fun getByName(name: String): Skill {
    return skillRepository.findByName(name) ?: throw SkillNotFound(name)
  }

  fun addSkill(skill: Skill): Skill {
    log.info("Adding skill with name {}", skill.name)
    return save(skill)
  }

  @ValidateSkill
  fun save(skill: Skill): Skill {
    if (skill.id == null) {
      val generatedId = generatorService.getAndIncrement("skill")
      return skillRepository.save(skill.copy(id = generatedId))
    }
    return skillRepository.findByName(skill.name) ?: throw SkillNotFound(skill.name)
  }

  fun findUserSkills(username: String): List<Skill> {
    return skillRepository.findByUsername(username)
  }

  fun getAll(): List<Skill> {
    return skillRepository.findAll()
  }

}