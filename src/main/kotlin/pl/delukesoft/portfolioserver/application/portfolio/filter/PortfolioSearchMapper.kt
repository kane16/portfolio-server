package pl.delukesoft.portfolioserver.application.portfolio.filter

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resume.ResumeSearch

@Component
class PortfolioSearchMapper(
) {

  fun mapToSearch(portfolioSearch: PortfolioSearch): ResumeSearch {
    return ResumeSearch(
      portfolioSearch.skills,
      portfolioSearch.technologyDomain,
      portfolioSearch.business
    )
  }

}