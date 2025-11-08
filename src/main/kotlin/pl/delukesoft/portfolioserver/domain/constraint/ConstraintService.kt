package pl.delukesoft.portfolioserver.domain.constraint

import org.springframework.stereotype.Service
import pl.delukesoft.portfolioserver.domain.validation.ValidationResult

@Service
class ConstraintService(
  private val constraintRepository: ConstraintRepository
) {

  fun getConstraints(): List<FieldConstraint> {
    return constraintRepository.findCachedConstraints()
  }

  fun validateConstraint(path: String, value: String?): ValidationResult {
    return getConstraints().find { it.path == path }?.validate(value)
      ?: ValidationResult.build("Constraint not found for path: $path")
  }

}