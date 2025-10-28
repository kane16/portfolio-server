package pl.delukesoft.portfolioserver.domain.resume.hobby

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeModifyRepository

@Service
class HobbyService(
  private val resumeModifyRepository: ResumeModifyRepository
) {

  fun addHobbyToResume(hobby: Hobby, resume: Resume): Boolean {
    val hobbies = resume.hobbies + hobby
    return resumeModifyRepository.changeHobbiesInResume(hobbies, resume)
  }

  fun deleteHobbyFromResume(hobby: Hobby, resume: Resume): Boolean {
    val hobbies = resume.hobbies.filter { it.name != hobby.name }
    return resumeModifyRepository.changeHobbiesInResume(hobbies, resume)
  }


}