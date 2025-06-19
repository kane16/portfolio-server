package pl.delukesoft.portfolioserver.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.portfolio.domain.ResumeService
import pl.delukesoft.portfolioserver.portfolio.model.PortfolioDTO

@Component
class PortfolioFacade(
  private val resumeService: ResumeService,
  private val resumeMapper: ResumeMapper
) {

  fun getCvById(id: Long): PortfolioDTO {
    return resumeMapper.mapToDTO(resumeService.getCvById(id))
  }

  fun getDefaultCV(): PortfolioDTO {
    return resumeMapper.mapToDTO(resumeService.getDefaultCV())
  }

}