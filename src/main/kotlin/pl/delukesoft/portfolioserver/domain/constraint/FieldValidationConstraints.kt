package pl.delukesoft.portfolioserver.domain.constraint

data class FieldValidationConstraints(
  val minLength: Int,
  val maxLength: Int,
  val nullable: Boolean = true,
)
