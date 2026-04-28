package pl.delukesoft.portfolioserver.resume.validation

class ResumeValidatorResult(
  isValid: Boolean,
  val domainResults: List<DomainValidationResult>
): ValidationResult(
  isValid,
  if (isValid) ValidationStatus.VALID else ValidationStatus.INVALID,
  domainResults.flatMap { it.errors }
) {
}