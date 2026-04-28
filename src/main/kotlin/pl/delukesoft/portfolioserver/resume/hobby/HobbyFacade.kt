package pl.delukesoft.portfolioserver.resume.hobby

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.security.UserContext
import pl.delukesoft.portfolioserver.resume.ResumeService
import pl.delukesoft.portfolioserver.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.resume.hobby.HobbyService

@Component
class HobbyFacade(
  private val hobbyService: HobbyService,
  private val resumeService: ResumeService,
  private val userContext: UserContext
) {

  private val currentUser
    get() = requireNotNull(userContext.user) { "Authenticated user is required" }

  fun addHobbyToResume(resumeId: Long, hobbyName: String): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val hobby = Hobby(hobbyName)
    return hobbyService.addHobbyToResume(hobby, resumeVersion)
  }

  fun deleteHobbyFromResume(resumeId: Long, hobbyName: String): Boolean {
    val resumeVersion = resumeService.getResumeById(resumeId, currentUser)
    val hobby = Hobby(hobbyName)
    return hobbyService.deleteHobbyFromResume(hobby, resumeVersion)
  }

}