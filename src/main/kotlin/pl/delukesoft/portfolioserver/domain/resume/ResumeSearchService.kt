package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.stereotype.Service

@Service
class ResumeSearchService {

  fun filterResumeWithCriteria(resume: Resume, search: ResumeSearch): Resume {
    var filteredResume = resume
    if (search.skillNames.isNotEmpty()) {
      filteredResume = filterBySkills(filteredResume, search.skillNames)
    }
    if (search.domainNames.isNotEmpty()) {
      filteredResume = filterByDomains(filteredResume, search.domainNames)
    }
    if (search.businessNames.isNotEmpty()) {
      filteredResume = filterByBusiness(filteredResume, search.businessNames)
    }
    return filteredResume
  }

  private fun filterBySkills(resume: Resume, skillNames: List<String>): Resume {
    return resume.copy(
      skills = resume.skills.filter { it.name in skillNames },
      experience = resume.experience.filter { it.skills.any { it.skill.name in skillNames } }
    )
  }

  private fun filterByDomains(resume: Resume, domains: List<String>): Resume {
    return resume.copy(
      skills = resume.skills.filter { it.domains.any { it.name in domains } },
      experience = resume.experience.filter { it.skills.any { it.skill.domains.any { it.name in domains } } }
    )
  }

  private fun filterByBusiness(resume: Resume, businessNames: List<String>): Resume {
    return resume.copy(
      experience = resume.experience.filter { businessNames.contains(it.business.name) }
    )
  }

}