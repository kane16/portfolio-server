package pl.delukesoft.portfolioserver.domain.constraint

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult
import pl.delukesoft.portfolioserver.domain.validation.ValidationStatus

@Document("FieldConstraint")
data class FieldConstraint(
  @Id
  val id: Long? = null,
  val path: String,
  val constraints: FieldValidationConstraints
) {

  fun validate(value: String?): ValidationResult {
    if (!constraints.nullable && value == null) {
      return ValidationResult.build("$path is not nullable")
    }
    if (value != null) {
      if (value.trim().length < constraints.minLength) {
        return ValidationResult.build("$path length must be at least ${constraints.minLength}")
      }
      if (value.trim().length > constraints.maxLength) {
        return ValidationResult.build("$path length must be at most ${constraints.maxLength}")
      }
    }
    return ValidationResult(true, ValidationStatus.VALID)

  }

  companion object {

    fun build(path: String, minLength: Int? = null, maxLength: Int? = null): FieldConstraint {
      return FieldConstraint(
        path = path,
        constraints = FieldValidationConstraints(
          nullable = minLength == null && maxLength == null,
          minLength = minLength ?: 0,
          maxLength = maxLength ?: Int.MAX_VALUE
        )
      )
    }

  }

}