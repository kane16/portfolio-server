package pl.delukesoft.portfolioserver.application.resume.validation

data class SimpleValidationResultDTO(
  val isValid: Boolean,
  val domain: String,
  val errors: List<String>
)