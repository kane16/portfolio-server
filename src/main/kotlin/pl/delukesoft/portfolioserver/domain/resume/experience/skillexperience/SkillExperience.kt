package pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience

import pl.delukesoft.portfolioserver.domain.resume.skill.Skill

data class SkillExperience(
  val skill: Skill,
  val level: Int,
  val detail: String
)