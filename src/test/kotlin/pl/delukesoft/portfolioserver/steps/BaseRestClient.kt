package pl.delukesoft.portfolioserver.steps

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
@Profile("test")
class BaseRestClient(
    @Value("\${web.url}") baseUrl: String,
    @Value("\${web.login.url}") loginUrl: String,
) {

    private val logger = LoggerFactory.getLogger(BaseRestClient::class.java)

    private val restClient: RestClient = RestClient.builder()
        .baseUrl(baseUrl)
        .build()

    private var token: String? = null

    fun attachTokenToRequest(token: String) {
        logger.info("Attaching token to request")
        this.token = token
    }

    fun <R> get(endpoint: String, responseType: Class<R>): ResponseEntity<R> {
        val request = restClient.get()
            .uri(endpoint)
        
        token?.let { request.header("Authorization", "Bearer $it") }
        
        return request.retrieve()
            .toEntity(responseType)
    }

    fun <T: Any, R> post(endpoint: String, body: T, responseType: Class<R>): ResponseEntity<R> {
        val request = restClient.post()
            .uri(endpoint)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            
        token?.let { request.header("Authorization", "Bearer $it") }
        
        return request.body(body)
            .retrieve()
            .toEntity(responseType)
    }

    fun <T: Any, R> put(endpoint: String, body: T, responseType: Class<R>): ResponseEntity<R> {
        val request = restClient.put()
            .uri(endpoint)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            
        token?.let { request.header("Authorization", "Bearer $it") }
        
        return request.body(body)
            .retrieve()
            .toEntity(responseType)
    }

    fun <R> delete(endpoint: String, responseType: Class<R>): ResponseEntity<R> {
        val request = restClient.delete()
            .uri(endpoint)
            
        token?.let { request.header("Authorization", "Bearer $it") }
        
        return request.retrieve()
            .toEntity(responseType)
    }
}