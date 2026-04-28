package pl.delukesoft.portfolioserver.security

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import pl.delukesoft.portfolioserver.author.Author

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserContext {
  var user: User? = null
  var author: Author? = null
}