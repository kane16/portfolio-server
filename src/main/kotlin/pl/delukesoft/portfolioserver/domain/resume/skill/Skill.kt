package pl.delukesoft.portfolioserver.domain.resume.skill

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain

@Document(collection = "Skill")
data class Skill(
  @Id val id: Long? = null,
  val name: String,
  val level: Int,
  val description: String? = null,
  val username: String,
  @DBRef(lazy = false) val domains: List<SkillDomain> = emptyList()
)
