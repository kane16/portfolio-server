package pl.delukesoft.portfolioserver.adapters.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.adapters.resume.PortfolioSearch
import pl.delukesoft.portfolioserver.adapters.resume.ResumeFacade
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.ResumeService

@Component
class PortfolioFacade(
  private val resumeFacade: ResumeFacade,
  private val resumeMapper: ResumeMapper
) {

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): PortfolioDTO {
    return resumeMapper.mapToDTO(resumeFacade.getCvById(id, portfolioSearch))
  }

  fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): PortfolioDTO {
    return resumeMapper.mapToDTO(resumeFacade.getDefaultCV(portfolioSearch))
  }

}