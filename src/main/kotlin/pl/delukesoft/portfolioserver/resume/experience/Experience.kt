package pl.delukesoft.portfolioserver.resume.experience

import pl.delukesoft.portfolioserver.resume.experience.business.Business
import pl.delukesoft.portfolioserver.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.WithConstraints

data class Experience(
  val id: Long? = null,
  val business: Business,
  val position: String,
  val summary: String,
  val description: String? = null,
  val timeframe: Timeframe,
  val skills: List<SkillExperience> = emptyList()
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.experience.summary", summary),
      validationFunc("resume.experience.position", position),
      validationFunc("resume.experience.description", description)
    )
  }

}