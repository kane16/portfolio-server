package pl.delukesoft.portfolioserver.portfolio

import org.springframework.stereotype.Component

@Component
class PortfolioFacade(
  private val curriculumService: CurriculumService,
  private val curriculumMapper: CurriculumMapper
) {

  fun getCvById(id: Long): PortfolioDTO {
    return curriculumMapper.mapToDTO(curriculumService.getCvById(id))
  }

  fun getDefaultCV(): PortfolioDTO {
    return curriculumMapper.mapToDTO(curriculumService.getDefaultCV())
  }

}