package pl.delukesoft.portfolioserver.author

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.resume.experience.business.Business
import pl.delukesoft.portfolioserver.resume.skill.Skill
import pl.delukesoft.portfolioserver.resume.skill.domain.SkillDomain

@Document(collection = "Author")
data class Author(
  @Id
  val id: Long? = null,
  val firstname: String,
  val lastname: String,
  val username: String,
  val email: String,
  val roles: List<String> = emptyList(),
  val skills: List<Skill> = emptyList(),
  val domains: List<SkillDomain> = emptyList(),
  val businesses: List<Business> = emptyList()
)
