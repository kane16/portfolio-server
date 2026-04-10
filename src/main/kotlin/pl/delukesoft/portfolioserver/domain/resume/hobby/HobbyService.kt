package pl.delukesoft.portfolioserver.domain.resume.hobby

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.ResumeModifyRepository
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion

@Service
class HobbyService(
  private val resumeModifyRepository: ResumeModifyRepository
) {

  fun addHobbyToResume(hobby: Hobby, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val hobbies = resume.hobbies + hobby
    return resumeModifyRepository.changeHobbiesInResume(hobbies, resumeVersion)
  }

  fun deleteHobbyFromResume(hobby: Hobby, resumeVersion: ResumeVersion): Boolean {
    val resume = resumeVersion.resume
    val hobbies = resume.hobbies.filter { it.name != hobby.name }
    return resumeModifyRepository.changeHobbiesInResume(hobbies, resumeVersion)
  }


}