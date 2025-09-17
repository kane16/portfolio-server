package pl.delukesoft.portfolioserver.application.resume

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.resume.exception.DomainNotFound
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
        ValidationDomainDTO.entries.find { it.label == domainResult.domainName }
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