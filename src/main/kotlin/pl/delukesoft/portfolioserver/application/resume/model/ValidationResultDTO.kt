package pl.delukesoft.portfolioserver.application.resume.model

class ValidationResultDTO(
  val isValid: Boolean,
  val progress: Double,
  val validationResults: List<ValidationDomainResultDTO>
)