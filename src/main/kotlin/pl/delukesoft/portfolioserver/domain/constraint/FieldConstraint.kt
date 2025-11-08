package pl.delukesoft.portfolioserver.domain.constraint

import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus

@Document("FieldConstraint")
data class FieldConstraint(
  val id: Long? = null,
  val path: String,
  val constraints: FieldValidationConstraints
) {

  fun validate(value: String?): ValidationResult {
    if (!constraints.nullable && value == null) {
      return ValidationResult.build("$path is not nullable")
    }
    if (value != null) {
      if (value.length < constraints.minLength) {
        return ValidationResult.build("$path length must be at least ${constraints.minLength}")
      }
      if (value.length > constraints.maxLength) {
        return ValidationResult.build("$path length must be at most ${constraints.maxLength}")
      }
    }
    return ValidationResult(true, ValidationStatus.VALID)

  }

}