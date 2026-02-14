package pl.delukesoft.portfolioserver.adapters.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Instant
import java.util.function.Function

@Service
@EnableConfigurationProperties(JwtProperties::class)
class AuthService(
  private val jwtProperties: JwtProperties
) {

  val secretKey = Jwts.SIG.HS512.key()
    .random(SecureRandom(jwtProperties.secret.toByteArray()))
    .build()

  fun getUser(tokenWithBearer: String): User {
    val token = extractToken(tokenWithBearer)
    if (!isTokenValid(token)) throw InvalidTokenException("Invalid JWT Token")
    return User(
      username = extractUsernameFromToken(token),
      email = extractClaim(token, { it["email"].toString() }),
      roles = extractClaim(token, { it["roles"].toString().split(",") })
    )
  }

  fun extractKeyFromClaim(key: String, claims: Claims): String =
    claims.get(key).toString()

  private fun extractToken(tokenWithBearer: String): String =
    tokenWithBearer.substringAfter("${jwtProperties.tokenPrefix} ")

  private fun isTokenValid(token: String): Boolean {
    val expirationDate: Instant = extractClaim(token, Claims::getExpiration).toInstant()
    return expirationDate.isAfter(Instant.now())
  }

  private fun extractUsernameFromToken(token: String): String =
    extractClaim(token, Claims::getSubject)


  private fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
    val claims = extractClaims(token)
    return claimsResolver.apply(claims)
  }

  fun extractClaims(token: String?): Claims {
    try {
      return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload()
    } catch (e: Exception) {
      throw InvalidTokenException("Invalid JWT Token")
    }
  }

}