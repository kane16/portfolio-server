package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.portfolio.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.application.resume.ResumeFacade

@Component
class PortfolioFacade(
  private val resumeFacade: ResumeFacade,
  private val portfolioMapper: PortfolioMapper,
) {

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): PortfolioDTO =
    portfolioMapper.mapToDTO(resumeFacade.getById(id, portfolioSearch))

  @Cacheable("portfolio", key = "#portfolioSearch?.toString() ?: 'default'")
  fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): PortfolioDTO =
    portfolioMapper.mapToDTO(resumeFacade.getDefaultCV(portfolioSearch))

}