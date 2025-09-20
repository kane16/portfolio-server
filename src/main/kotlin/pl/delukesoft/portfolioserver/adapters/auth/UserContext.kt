package pl.delukesoft.portfolioserver.adapters.auth

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import pl.delukesoft.portfolioserver.domain.user.Author

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserContext {
  var user: User? = null
  var author: Author? = null
}