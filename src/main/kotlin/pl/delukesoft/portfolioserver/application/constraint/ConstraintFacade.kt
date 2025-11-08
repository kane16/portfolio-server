package pl.delukesoft.portfolioserver.application.constraint

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.domain.constraint.ConstraintService

@Component
class ConstraintFacade(
  private val constraintService: ConstraintService
) {

  fun getResumeConstraints(): List<ConstraintDTO> {
    return constraintService.getConstraints().map {
      ConstraintDTO(
        path = it.path,
        constraints = it.constraints
      )
    }
  }

}