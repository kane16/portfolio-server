package pl.delukesoft.portfolioserver.resume.validation.constraint

import org.springframework.stereotype.Component

@Component
class ConstraintFacade(
  private val constraintService: ConstraintService
) {

  fun getResumeConstraints(): List<ConstraintDTO> {
    return constraintService.getConstraintsForEndpoint().map {
      ConstraintDTO(
        path = it.path,
        constraints = it.constraints
      )
    }
  }

}
