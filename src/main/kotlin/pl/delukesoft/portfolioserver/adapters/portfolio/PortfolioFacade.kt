package pl.delukesoft.portfolioserver.adapters.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.domain.resume.read.ResumeResumeService

@Component
class PortfolioFacade(
  private val resumeResumeService: ResumeResumeService,
  private val resumeMapper: ResumeMapper
) {

  fun getCvById(id: Long): PortfolioDTO {
    return resumeMapper.mapToDTO(resumeResumeService.getCvById(id))
  }

  fun getDefaultCV(): PortfolioDTO {
    return resumeMapper.mapToDTO(resumeResumeService.getDefaultCV())
  }

}