package pl.delukesoft.portfolioserver.adapters.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.domain.resume.read.ResumeService

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