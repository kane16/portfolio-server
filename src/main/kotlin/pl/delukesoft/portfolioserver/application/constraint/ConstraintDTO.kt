package pl.delukesoft.portfolioserver.application.constraint

import pl.delukesoft.portfolioserver.domain.constraint.FieldValidationConstraints

data class ConstraintDTO(
  val path: String,
  val constraints: FieldValidationConstraints
)