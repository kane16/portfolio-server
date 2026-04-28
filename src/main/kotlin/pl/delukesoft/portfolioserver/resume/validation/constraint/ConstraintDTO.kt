package pl.delukesoft.portfolioserver.resume.validation.constraint

import pl.delukesoft.portfolioserver.resume.validation.constraint.FieldValidationConstraints

data class ConstraintDTO(
  val path: String,
  val constraints: FieldValidationConstraints
)