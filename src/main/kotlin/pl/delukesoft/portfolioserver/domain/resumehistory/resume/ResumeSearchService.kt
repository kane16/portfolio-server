package pl.delukesoft.portfolioserver.domain.resumehistory.resume

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.skill.domain.SkillDomain

@Service
class ResumeSearchService {

  fun filterResumeWithCriteria(resume: Resume, search: ResumeSearch): Resume {
    var filteredResume = resume
    if (search.skills.isNotEmpty()) {
      filteredResume = filterBySkills(filteredResume, search.skills)
    }
    if (search.domains.isNotEmpty()) {
      filteredResume = filterByDomains(filteredResume, search.domains)
    }
    if (search.business.isNotEmpty()) {
      filteredResume = filterByBusiness(filteredResume, search.business)
    }
    return filteredResume
  }

  private fun filterBySkills(resume: Resume, skills: List<Skill>): Resume {
    val skillIds = skills.map { it.id }
    return resume.copy(
      skills = resume.skills.filter { it.id in skillIds },
      experience = resume.experience.filter { it.skills.any { it.skill.id in skillIds } }
    )
  }

  private fun filterByDomains(resume: Resume, domains: List<SkillDomain>): Resume {
    val businessDomainIds = domains.map { it.id }
    return resume.copy(
      skills = resume.skills.filter { it.domains.any { it.id in businessDomainIds} },
      experience = resume.experience.filter { it.skills.any { it.skill.domains.any { it.id in businessDomainIds} } }
    )
  }

  private fun filterByBusiness(resume: Resume, business: List<Business>): Resume {
    val businessIds = business.map { it.id }
    return resume.copy(
      experience = resume.experience.filter { it.business.id in businessIds }
    )
  }

}