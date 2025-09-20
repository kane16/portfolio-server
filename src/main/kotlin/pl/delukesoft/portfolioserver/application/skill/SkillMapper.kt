package pl.delukesoft.portfolioserver.application.skill

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain

@Component
@RegisterReflectionForBinding(SkillDTO::class)
class SkillMapper {

  fun mapToSkill(skillDTO: SkillDTO, availableDomains: List<SkillDomain>): Skill =
    Skill(
      skillDTO.name,
      skillDTO.level,
      skillDTO.description,
      availableDomains.filter { it.name in skillDTO.domains }
    )

  fun mapToSkillDomain(domain: String, availableDomains: List<SkillDomain>): SkillDomain =
    availableDomains.find { it.name == domain } ?: SkillDomain(
      name = domain,
    )

  fun mapToDTO(skill: Skill): SkillDTO {
    return SkillDTO(
      skill.name,
      skill.level,
      skill.description ?: "",
      skill.domains.map { it.name }
    )
  }

}