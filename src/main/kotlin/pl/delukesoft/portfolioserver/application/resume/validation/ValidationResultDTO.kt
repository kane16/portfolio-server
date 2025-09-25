package pl.delukesoft.portfolioserver.application.resume.validation

data class ValidationResultDTO(
  val isValid: Boolean,
  val progress: Int,
  val validationResults: List<ValidationDomainResultDTO>
)