package pl.delukesoft.portfolioserver.domain.resume.experience

import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.WithConstraints

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