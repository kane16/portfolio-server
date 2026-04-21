package pl.delukesoft.portfolioserver.adapters.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.time.Instant
import java.util.function.Function

@Service
@EnableConfigurationProperties(JwtProperties::class)
class AuthTokenService(
  private val jwtProperties: JwtProperties
) {

  val secretKey = Keys.hmacShaKeyFor(
    MessageDigest.getInstance("SHA-512")
      .digest(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))
  )

  fun getUser(tokenWithBearer: String): User {
    val token = extractToken(tokenWithBearer)
    if (!isTokenValid(token)) throw InvalidTokenException("Invalid JWT Token")
    if (!isUserValid(token)) throw InvalidTokenException("User claims are missing in JWT Token")
    return User(
      username = extractUsernameFromToken(token),
      email = extractClaim(token, { it["email"].toString() }),
      roles = extractClaim(token, { it["roles"].toString().split(",") }),
      firstname = extractClaim(token, { it["firstname"].toString() }),
      lastname = extractClaim(token, { it["lastname"].toString() }),
    )
  }

  fun isUserValid(token: String): Boolean {
    val userClaims = listOf(
      "firstname",
      "lastname",
      "email",
      "roles"
    ).map { extractKeyFromClaim(it, extractClaims(token)) }
    return userClaims.all { it.isNotBlank() }
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
      throw InvalidTokenException(e.message ?: "Invalid JWT Token")
    }
  }

}