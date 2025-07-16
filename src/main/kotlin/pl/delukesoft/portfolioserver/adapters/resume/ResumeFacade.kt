package pl.delukesoft.portfolioserver.adapters.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.Resume
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.ResumeSearchService
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.ResumeService

@Component
class ResumeFacade(
  private val resumeService: ResumeService,
  private val resumeSearchService: ResumeSearchService,
  private val portfolioSearchMapper: PortfolioSearchMapper,
) {

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): Resume {
    val resumeById = resumeService.getCvById(id)
    return getResumeWithOptionalFilter(resumeById, portfolioSearch)
  }

  fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): Resume {
    val defaultResume = resumeService.getDefaultCV()
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