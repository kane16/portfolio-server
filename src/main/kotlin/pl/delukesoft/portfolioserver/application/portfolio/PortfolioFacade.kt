package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.domain.resume.ResumeFacade

@Component
class PortfolioFacade(
  private val resumeFacade: ResumeFacade,
  private val resumeMapper: PortfolioMapper
) {

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): PortfolioDTO {
    return resumeMapper.mapToDTO(resumeFacade.getCvById(id, portfolioSearch))
  }

  fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): PortfolioDTO {
    return resumeMapper.mapToDTO(resumeFacade.getDefaultCV(portfolioSearch))
  }

}