package pl.delukesoft.portfolioserver.application.resume.hobby

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.hobby.HobbyService

@Component
class HobbyFacade(
  private val hobbyService: HobbyService,
  private val resumeService: ResumeService,
  private val userContext: UserContext
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addHobbyToResume(resumeId: Long, hobbyName: String): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val hobby = Hobby(hobbyName)
    return hobbyService.addHobbyToResume(hobby, resume)
  }

  fun deleteHobbyFromResume(resumeId: Long, hobbyName: String): Boolean {
    val resume = resumeService.getResumeById(resumeId, currentUser)
    val hobby = Hobby(hobbyName)
    return hobbyService.deleteHobbyFromResume(hobby, resume)
  }

}