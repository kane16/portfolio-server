package pl.delukesoft.portfolioserver.resume.hobby

import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.portfolioserver.resume.ResumeService

@Component
class HobbyFacade(
  private val hobbyService: HobbyService,
  private val resumeService: ResumeService,
  private val authContext: AuthContext
) {

  private val currentUser
    get() = requireNotNull(authContext.user) { "Authenticated user is required" }

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