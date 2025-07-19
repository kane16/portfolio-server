package pl.delukesoft.portfolioserver.domain.resume

import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomain


data class ResumeSearch(
  val skills: List<Skill>,
  val domains: List<SkillDomain>,
  val business: List<Business>
) {

}
