package pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill.domain

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "TechDomain")
data class SkillDomain(
  val id: Long? = null,
  val name: String,
)