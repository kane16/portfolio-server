package pl.delukesoft.portfolioserver.domain.validation

class DomainValidationResult(
  val domainName: String,
  isValid: Boolean,
  validationStatus: ValidationStatus,
  errors: List<String>
): ValidationResult(
  isValid,
  validationStatus,
  errors
) {

  companion object {

    fun build(domain: String, validationResult: ValidationResult): DomainValidationResult {
      return DomainValidationResult(
        domain,
        validationResult.isValid,
        validationResult.validationStatus,
        validationResult.errors
      )
    }

  }

}