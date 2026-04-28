package pl.delukesoft.portfolioserver.resume.education

import pl.delukesoft.portfolioserver.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.resume.validation.ValidationResult
import pl.delukesoft.portfolioserver.resume.validation.WithConstraints

data class Education(
  val id: Long? = null,
  val title: String,
  val institution: EducationInstitution,
  val timeframe: Timeframe,
  val fieldOfStudy: String,
  val grade: Double,
  val type: EducationType? = EducationType.DEGREE,
  val description: String? = null,
  val externalLinks: List<String> = emptyList()
) : WithConstraints {

  override fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult> {
    return listOf(
      validationFunc("resume.education.title", title)
    )
  }


}
