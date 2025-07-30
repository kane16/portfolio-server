package pl.delukesoft.portfolioserver.domain.resumehistory

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext

@Component
class ResumeHistoryFacade(
  val userContext: UserContext,
  val resumeHistoryService: ResumeHistoryService
) {

  fun getUserHistory(): ResumeHistory {
    return resumeHistoryService.findByUsername(userContext.user?.username!!)
  }

}