package pl.delukesoft.portfolioserver.domain.resumehistory

import pl.delukesoft.portfolioserver.adapters.auth.UserContext

class ResumeHistoryFacade(
  val userContext: UserContext,
  val resumeHistoryService: ResumeHistoryService
) {



}