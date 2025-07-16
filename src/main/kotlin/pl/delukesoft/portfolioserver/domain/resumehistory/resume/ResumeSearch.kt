package pl.delukesoft.portfolioserver.domain.resumehistory.resume

import pl.delukesoft.portfolioserver.domain.resumehistory.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill.domain.SkillDomain

data class ResumeSearch(
  val skills: List<Skill>,
  val domains: List<SkillDomain>,
  val business: List<Business>
) {

}
