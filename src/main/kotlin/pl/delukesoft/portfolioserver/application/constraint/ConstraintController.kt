package pl.delukesoft.portfolioserver.application.constraint

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@RequestMapping("/constraints")
class ConstraintController(
  private val constraintFacade: ConstraintFacade
) {

  @GetMapping
  @AuthRequired("ROLE_CANDIDATE")
  fun getResumeConstraints(): List<ConstraintDTO> {
    return constraintFacade.getResumeConstraints()
  }

}