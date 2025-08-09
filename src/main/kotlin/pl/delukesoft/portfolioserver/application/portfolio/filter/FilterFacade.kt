package pl.delukesoft.portfolioserver.application.portfolio.filter

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.experience.business.BusinessService
import pl.delukesoft.portfolioserver.domain.resume.skill.SkillService
import pl.delukesoft.portfolioserver.domain.resume.skill.domain.SkillDomainService
import java.util.concurrent.Executors

@Component
class FilterFacade(
  private val skillService: SkillService,
  private val businessService: BusinessService,
  private val domainService: SkillDomainService
) {

  fun getAllSkills(): List<String> {
    return skillService.getAll().map { it.name }
  }

  fun getAllBusiness(): List<String> {
    return businessService.getAll().map { it.name }
  }

  fun getAllDomains(): List<String> {
    return domainService.getAll().map { it.name }
  }

  fun getAllFilters(): PortfolioSearch {
    Executors.newVirtualThreadPerTaskExecutor().use { pool ->
      val skills = pool.submit<List<String>> { getAllSkills() }
      val business = pool.submit<List<String>> { getAllBusiness() }
      val domains = pool.submit<List<String>> { getAllDomains() }
      return PortfolioSearch(
        business.get(),
        skills.get(),
        domains.get()
      )
    }
  }

}