package pl.delukesoft.portfolioserver.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain

@Document(collection = "Author")
data class Author(
  @Id
  val id: Long? = null,
  val username: String,
  val email: String,
  val roles: List<String> = emptyList(),
  val skills: List<Skill> = emptyList(),
  val domains: List<SkillDomain> = emptyList(),
  val businesses: List<Business> = emptyList()
)
