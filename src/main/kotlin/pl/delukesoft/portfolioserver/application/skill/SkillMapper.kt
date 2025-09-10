package pl.delukesoft.portfolioserver.application.skill

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain

@Component
@RegisterReflectionForBinding(SkillDTO::class)
class SkillMapper {

  fun mapToSkill(skillDTO: SkillDTO, username: String, availableDomains: List<SkillDomain>): Skill =
    Skill(
      skillDTO.id,
      skillDTO.name,
      skillDTO.level,
      skillDTO.description,
      username,
      availableDomains.filter { it.name in skillDTO.domains }
    )

  fun mapToSkillDomain(domain: String, username: String, availableDomains: List<SkillDomain>): SkillDomain =
    availableDomains.find { it.name == domain } ?: SkillDomain(
      name = domain,
      username = username
    )

}