package pl.delukesoft.portfolioserver.domain.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.application.filter.PortfolioSearchMapper
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioHistoryDTO
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory

@Component
class ResumeFacade(
  private val resumeService: ResumeService,
  private val resumeSearchService: ResumeSearchService,
  private val portfolioSearchMapper: PortfolioSearchMapper,
  private val userContext: UserContext
) {

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): Resume {
    val resumeById = resumeService.getCvById(id, userContext.user)
    return getResumeWithOptionalFilter(resumeById, portfolioSearch)
  }

  fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): Resume {
    val defaultResume = resumeService.getDefaultCV(userContext.user)
    return getResumeWithOptionalFilter(defaultResume, portfolioSearch)
  }

  private fun getResumeWithOptionalFilter(
    defaultResume: Resume,
    portfolioSearch: PortfolioSearch?
  ): Resume {
    val resumeSearch = portfolioSearch?.let { portfolioSearchMapper.mapToSearch(it) }
    return when (resumeSearch) {
      null -> defaultResume
      else -> resumeSearchService.filterResumeWithCriteria(defaultResume, resumeSearch)
    }
  }

}