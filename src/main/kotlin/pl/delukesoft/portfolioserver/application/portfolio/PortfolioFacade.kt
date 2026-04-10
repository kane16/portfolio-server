package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.application.resume.ResumeFacade

@Component
class PortfolioFacade(
  private val resumeFacade: ResumeFacade,
  private val portfolioMapper: PortfolioMapper,
) {

  @Cacheable("portfolio", key = "#id")
  fun getCvById(id: Long): PortfolioDTO =
    portfolioMapper.mapToDTO(resumeFacade.getById(id))

  @Cacheable("portfolio")
  fun getDefaultCV(): PortfolioDTO =
    portfolioMapper.mapToDTO(resumeFacade.getDefaultCV())

}