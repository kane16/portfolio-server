package pl.delukesoft.portfolioserver.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
  val secret: String,
  val issuer: String = "central-auth-server",
  val accessTokenExpirationMinutes: Long = 15,
  val refreshTokenExpirationDays: Long = 7,
  val authorizationHeader: String = "Authorization",
  val tokenPrefix: String = "Bearer"
)
