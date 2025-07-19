package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.ResumeSearch
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessService
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillService
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainService

@Component
class PortfolioSearchMapper(
  private val skillService: SkillService,
  private val skillDomainService: SkillDomainService,
  private val businessService: BusinessService
) {

  fun mapToSearch(portfolioSearch: PortfolioSearch): ResumeSearch {
    return ResumeSearch(
      portfolioSearch.skills.map { skillService.getByName(it) },
      portfolioSearch.technologyDomain.map { skillDomainService.getByName(it) },
      portfolioSearch.business.map { businessService.getByName(it) }
    )
  }

}