package pl.delukesoft.portfolioserver.domain.resume.write

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.model.Skill
import pl.delukesoft.portfolioserver.domain.resume.print.GeneratorService

@Service
class SkillService(
  private val skillWriteRepository: SkillWriteRepository,
  private val generatorService: GeneratorService
) {

  fun saveAll(skills: List<Skill>): List<Skill> {
    return skills.map { save(it) }
  }

  fun save(skill: Skill): Skill {
    if (skill.id == null) {
      val generatedId = generatorService.getAndIncrement("skill")
      return skillWriteRepository.save(skill.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

}