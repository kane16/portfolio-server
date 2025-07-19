package pl.delukesoft.portfolioserver.domain.resume.skill

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.skill.exception.SkillNotFound
import pl.delukesoft.portfolioserver.domain.sequence.GeneratorService

@Service
class SkillService(
  private val skillRepository: SkillRepository,
  private val generatorService: GeneratorService
) {

  fun getByName(name: String): Skill {
    return skillRepository.findByName(name) ?: throw SkillNotFound(name)
  }

  fun saveAll(skills: List<Skill>): List<Skill> {
    return skills.map { save(it) }
  }

  fun save(skill: Skill): Skill {
    if (skill.id == null) {
      val generatedId = generatorService.getAndIncrement("skill")
      return skillRepository.save(skill.copy(id = generatedId))
    }
    return skillRepository.findByName(skill.name) ?: throw SkillNotFound(skill.name)
  }

  fun getAll(): List<Skill> {
    return skillRepository.findAll()
  }

}