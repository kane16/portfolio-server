package pl.delukesoft.portfolioserver.resume.validation.constraint

data class FieldValidationConstraints(
  val minLength: Int,
  val maxLength: Int,
  val nullable: Boolean = true,
)
