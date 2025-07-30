package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.filter.FilterFacade
import pl.delukesoft.portfolioserver.application.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioHistoryDTO
import pl.delukesoft.portfolioserver.domain.resume.ResumeFacade
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryFacade

@Component
class PortfolioFacade(
  private val resumeFacade: ResumeFacade,
  private val resumeHistoryFacade: ResumeHistoryFacade,
  private val portfolioMapper: PortfolioMapper
) {

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): PortfolioDTO {
    return portfolioMapper.mapToDTO(resumeFacade.getCvById(id, portfolioSearch))
  }

  fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): PortfolioDTO {
    return portfolioMapper.mapToDTO(resumeFacade.getDefaultCV(portfolioSearch))
  }

  fun getUserHistory(): PortfolioHistoryDTO {
    return portfolioMapper.mapHistoryToDTO(resumeHistoryFacade.getUserHistory())
  }

}