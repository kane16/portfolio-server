package pl.delukesoft.portfolioserver.cv

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.delukesoft.portfolioserver.auth.AuthRequired

@RestController
@RequestMapping("cv")
class CvController {

  @GetMapping
  @AuthRequired(role = "ROLE_ADMIN")
  fun getCv(
    @RequestHeader("Authorization") token: String,
  ): String {
    return "CV"
  }

}