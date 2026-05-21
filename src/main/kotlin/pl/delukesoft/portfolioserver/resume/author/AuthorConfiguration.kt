package pl.delukesoft.portfolioserver.resume.author

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.delukesoft.authplugin.security.AuthContext

@Configuration
class AuthorConfiguration {

  @Bean
  fun portfolioAuthContext(): AuthContext<PortfolioAuthor> {
    return AuthContext<PortfolioAuthor>()
  }


}