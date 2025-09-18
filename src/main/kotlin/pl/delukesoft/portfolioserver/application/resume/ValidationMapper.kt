package pl.delukesoft.portfolioserver.application.resume

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.resume.exception.DomainNotFound
import pl.delukesoft.portfolioserver.application.resume.model.ValidationDomain
import pl.delukesoft.portfolioserver.application.resume.model.ValidationDomainDTO
import pl.delukesoft.portfolioserver.application.resume.model.ValidationDomainResultDTO
import pl.delukesoft.portfolioserver.application.resume.model.ValidationResultDTO
import pl.delukesoft.portfolioserver.domain.validation.ResumeValidatorResult
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

}