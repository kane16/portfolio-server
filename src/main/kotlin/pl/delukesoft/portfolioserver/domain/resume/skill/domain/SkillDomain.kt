package pl.delukesoft.portfolioserver.domain.resume.skill.domain

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "TechDomain")
data class SkillDomain(
  val id: Long? = null,
  val name: String,
  val username: String
)