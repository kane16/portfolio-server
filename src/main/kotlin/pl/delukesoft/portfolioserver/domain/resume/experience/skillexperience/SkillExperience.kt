package pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience

import org.springframework.data.mongodb.core.mapping.DBRef
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill

data class SkillExperience(
  @DBRef val skill: Skill,
  val level: Int,
  val detail: String
)