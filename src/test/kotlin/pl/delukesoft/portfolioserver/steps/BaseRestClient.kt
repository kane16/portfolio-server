package pl.delukesoft.portfolioserver.steps

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import kotlin.jvm.java

@Component
@Profile("test")
class BaseRestClient(
    @Value("\${web.url}") baseUrl: String,
    @Value("\${web.login.url}") loginUrl: String,
) {

    private val loginClient: RestClient = RestClient.builder()
        .baseUrl(loginUrl)
        .build()

    private val restClient: RestClient = RestClient.builder()
        .baseUrl(baseUrl)
        .build()

    private var token: String? = null

    fun attachTokenWithLoginRequest(credentialsJson: String) {
        val userTokenDTO = loginClient.post()
            .accept(MediaType.APPLICATION_JSON)
            .body(credentialsJson)
            .retrieve()
            .toEntity(UserTokenDTO::class.java)
            .body

        userTokenDTO?.let {
            this.token = it.jwtDesc
        }
    }

    fun <R> get(endpoint: String, responseType: Class<R>): ResponseEntity<R> {
        return restClient.get()
            .uri(endpoint)
            .header("Authorization", "Bearer ${token}")
            .retrieve()
            .toEntity(responseType)
    }

    fun <T: Any, R> post(endpoint: String, body: T, responseType: Class<R>): ResponseEntity<R> {
        return restClient.post()
            .uri(endpoint)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${token}")
            .body(body)
            .retrieve()
            .toEntity(responseType)
    }

    fun <T: Any, R> put(endpoint: String, body: T, responseType: Class<R>): ResponseEntity<R> {
        return restClient.put()
            .uri(endpoint)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${token}")
            .body(body)
            .retrieve()
            .toEntity(responseType)
    }

    fun <R> delete(endpoint: String, responseType: Class<R>): ResponseEntity<R> {
        return restClient.delete()
            .uri(endpoint)
            .header("Authorization", "Bearer ${token}")
            .retrieve()
            .toEntity(responseType)
    }
}
