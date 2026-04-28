package pl.delukesoft.portfolioserver.security

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpRequest
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import pl.delukesoft.portfolioserver.security.exception.AuthorizationException

@RegisterReflectionForBinding(AppUserDTO::class)
@Service
class AuthRequestService(
  @Value("\${auth.api.url:}") private val authApiUrl: String
) {

  private val httpClient = RestClient.builder()
    .baseUrl(authApiUrl)
    .defaultHeader("Content-Type", "application/json")
    .defaultHeader("Accept", "application/json")
    .defaultHeader("Accept-Charset", "UTF-8")
    .build()

  fun getUserById(token: String, id: Long): AppUserDTO? {
    return getAllUsers(token).firstOrNull { it.id == id }
  }

  fun getAllUsers(token: String): List<AppUserDTO> {
    return httpClient.get()
      .uri { it.path("/users").build() }
      .header("Authorization", token)
      .exchange(exchangeUsers) ?: emptyList()
  }

  private val exchangeUsers =
    { _: HttpRequest, clientResponse: RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse ->
      if (clientResponse.statusCode.is2xxSuccessful) {
        clientResponse.bodyTo(Array<AppUserDTO>::class.java)?.toList()
      } else {
        throw AuthorizationException("Users not found with status: ${clientResponse.statusCode}")
      }
    }

}