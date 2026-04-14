package pl.delukesoft.portfolioserver.application.constraint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@RequestMapping("/constraints")
@Tag(name = "Constraints", description = "Resume constraint definitions")
class ConstraintController(
  private val constraintFacade: ConstraintFacade
) {

  @GetMapping
  @AuthRequired("ROLE_CANDIDATE")
  @Operation(summary = "Get resume constraints", description = "Retrieve all resume constraint definitions")
  @SecurityRequirement(name = "Bearer Authentication")
  fun getResumeConstraints(): List<ConstraintDTO> {
    return constraintFacade.getResumeConstraints()
  }

}