package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.application.portfolio.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioHistoryDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioShortcutDTO
import pl.delukesoft.portfolioserver.domain.resume.ResumeFacade
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryFacade

@Component
class PortfolioFacade(
  private val resumeFacade: ResumeFacade,
  private val resumeHistoryFacade: ResumeHistoryFacade,
  private val portfolioMapper: PortfolioMapper,
  private val userContext: UserContext
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun getCvById(id: Long, portfolioSearch: PortfolioSearch? = null): PortfolioDTO =
    portfolioMapper.mapToDTO(resumeFacade.getCvById(id, portfolioSearch))

  fun getDefaultCV(portfolioSearch: PortfolioSearch? = null): PortfolioDTO =
    portfolioMapper.mapToDTO(resumeFacade.getDefaultCV(portfolioSearch))

  fun getUserHistory(): PortfolioHistoryDTO =
    portfolioMapper.mapHistoryToDTO(resumeHistoryFacade.getUserHistory())

  fun initiatePortfolio(shortcut: PortfolioShortcutDTO): Boolean {
    val resumeWithShortcutOnly = portfolioMapper.mapShortcutDTOToResume(shortcut, currentUser)
    return resumeFacade.initiateResume(resumeWithShortcutOnly)
  }

  fun unpublishPortfolio(portfolioVersion: Long): Boolean {
    val resumeVersion = resumeHistoryFacade.getUserPublishedVersion()
    val pulledVersion = resumeHistoryFacade.getUserVersion(portfolioVersion)
    return resumeFacade.unpublishResume(resumeVersion, pulledVersion)
  }

  fun editPortfolio(id: Long, shortcut: PortfolioShortcutDTO): Boolean {
    val resumeShortcutToModify = portfolioMapper.mapShortcutDTOToResume(shortcut, currentUser, id)
    return resumeFacade.editResume(resumeShortcutToModify)
  }

  fun publishPortfolio(portfolioVersion: Long): Boolean {
    val publishedVersion = resumeHistoryFacade.getUserPublishedVersion()
    val versionToPublish = resumeHistoryFacade.getUserVersion(portfolioVersion)
    return resumeFacade.publishResume(publishedVersion, versionToPublish)
  }

}