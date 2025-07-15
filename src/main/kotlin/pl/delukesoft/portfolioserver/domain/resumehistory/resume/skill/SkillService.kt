package pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.print.GeneratorService

@Service
class SkillService(
  private val skillRepository: SkillRepository,
  private val generatorService: GeneratorService
) {

  fun saveAll(skills: List<Skill>): List<Skill> {
    return skills.map { save(it) }
  }

  fun save(skill: Skill): Skill {
    if (skill.id == null) {
      val generatedId = generatorService.getAndIncrement("skill")
      return skillRepository.save(skill.copy(id = generatedId))
    }
    TODO("Not yet implemented")
  }

}