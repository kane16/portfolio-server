package pl.delukesoft.portfolioserver.application.resume.model

data class ValidationResultDTO(
  val isValid: Boolean,
  val progress: Int,
  val validationResults: List<ValidationDomainResultDTO>
)