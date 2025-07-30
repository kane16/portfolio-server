package pl.delukesoft.portfolioserver.utility.loader.model

import pl.delukesoft.portfolioserver.adapters.image.Image
import java.time.LocalDateTime

data class UploadResume(
  val user: UploadUser,
  val title: String,
  val summary: String,
  val skills: List<UploadSkill>,
  val experience: List<UploadExperience>,
  val sideProjects: List<UploadExperience>,
  val image: Image? = null,
  val hobbies: List<String>,
  val languages: List<UploadWorkLanguage>,
  val createdOn: LocalDateTime,
  val lastModified: LocalDateTime
)