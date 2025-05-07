package pl.delukesoft.portfolioserver.exception

import com.mongodb.MongoException
import lombok.extern.slf4j.Slf4j
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

private data class ResponsePair(
  val first: String,
  val second: String
)

@ControllerAdvice
@RegisterReflectionForBinding(ResponsePair::class)
@Slf4j
class ErrorAdvisor : ResponseEntityExceptionHandler() {

  override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest): ResponseEntity<Any>? {
    val body = mapOf<String, Any>(
      Pair("timestamp", LocalDateTime.now()),
      Pair("field", ex.bindingResult.fieldError?.field ?: "Unknown"),
      Pair("error", ex.bindingResult.fieldError?.defaultMessage ?: "Validation failed"),
      Pair("validationErrors", ex.bindingResult.fieldErrors.map { ResponsePair(it.field, it.defaultMessage ?: "Validation failed") }),
      Pair("status", ex.statusCode.value())
    )
    return ResponseEntity(body, ex.statusCode)
  }

  @ExceptionHandler(ResponseStatusException::class)
  fun handleResponseStatusException(exc: ResponseStatusException, response: WebRequest): ResponseEntity<Any> {
    val body = mapOf<String, Any>(
      Pair("timestamp", LocalDateTime.now()),
      Pair("error", exc.reason!!),
      Pair("status", exc.statusCode.value())
    )
    return ResponseEntity(body, exc.statusCode)
  }

  @ExceptionHandler(MongoException::class)
  fun handleMongoErrors(exc: MongoException, response: WebRequest): ResponseEntity<Any> {
    val body = mapOf<String, Any>(
      Pair("timestamp", LocalDateTime.now()),
      Pair("status", 500),
      Pair("error", "Mongo exception encountered"),
    )
    logger.error("Mongo exception encountered", exc)
    return ResponseEntity(body, HttpStatusCode.valueOf(500))
  }

}