package pl.delukesoft.portfolioserver.resume.validation.constraint

data class ConstraintDTO(
  val path: String,
  val constraints: FieldValidationConstraints
)