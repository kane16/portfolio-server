package pl.delukesoft.portfolioserver.resume.validation

interface WithConstraints {

  fun validateConstraintPaths(validationFunc: (String, String?) -> ValidationResult): List<ValidationResult>

}