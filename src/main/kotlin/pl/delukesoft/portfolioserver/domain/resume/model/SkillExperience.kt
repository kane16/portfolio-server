package pl.delukesoft.portfolioserver.domain.resume.model

import org.springframework.data.mongodb.core.mapping.DBRef

data class SkillExperience(
  @DBRef val skill: Skill,
  val level: Int,
  val detail: String
)
