package pl.delukesoft.portfolioserver.application.resume

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.resume.exception.DomainNotFound
import pl.delukesoft.portfolioserver.application.resume.validation.*
import pl.delukesoft.portfolioserver.domain.validation.ResumeValidatorResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus

@Component
@RegisterReflectionForBinding(ValidationResultDTO::class, ValidationDomainResultDTO::class)
class ValidationMapper {

  fun mapResumeValidationResultToDTO(validationResult: ResumeValidatorResult): ValidationResultDTO {
    val domainResults: List<ValidationDomainResultDTO> = validationResult.domainResults.map { domainResult ->
      ValidationDomainResultDTO(
        domainResult.validationStatus,
        ValidationDomain.entries.filter { it.label == domainResult.domainName }
          .map { ValidationDomainDTO(it.title, it.weight, it.endpoint) }
          .firstOrNull()
          ?: throw DomainNotFound(domainResult.domainName),
        domainResult.errors.distinct()
      )
    }
    return ValidationResultDTO(
      validationResult.isValid,
      domainResults.filter { it.validationStatus == ValidationStatus.VALID }.sumOf { it.domain.weight },
      domainResults
    )
  }

  fun mapValidationResultToDTO(validationResult: ValidationResult, domain: String): SimpleValidationResultDTO {
    return SimpleValidationResultDTO(
      validationResult.isValid,
      domain,
      validationResult.errors
    )
  }

}