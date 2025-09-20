package pl.delukesoft.portfolioserver.domain.resume.skill

import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain

data class Skill(
  val name: String,
  val level: Int,
  val description: String? = null,
  val domains: List<SkillDomain> = emptyList()
)
