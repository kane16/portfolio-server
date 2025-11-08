package pl.delukesoft.portfolioserver.domain.validation

interface WithConstraints {

  fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult>

}