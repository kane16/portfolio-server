package pl.delukesoft.portfolioserver.resume.author

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.author.Author
import pl.delukesoft.authplugin.author.AuthorMapper
import pl.delukesoft.portfolioserver.platform.exception.LoggableResponseStatusException

@Component
class PortfolioAuthorMapper(
  val objectMapper: ObjectMapper
) : AuthorMapper<PortfolioAuthor> {

  override fun mapToApplicationAuthor(author: Author): PortfolioAuthor {
    try {
      val additionalInfo =
        objectMapper.convertValue<PortfolioAuthorAdditionalInfo>(author.additionalInfo)
      return PortfolioAuthor(
        author, additionalInfo
      )
    } catch (e: IllegalArgumentException) {
      throw LoggableResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid additional info format")
    }
  }

  override fun mapToAuthAuthor(applicationAuthor: PortfolioAuthor): Author {
    val additionalInfo = objectMapper.convertValue<JsonNode>(applicationAuthor.domainInfo)
    return Author(
      applicationAuthor.authAuthor.userId,
      applicationAuthor.authAuthor.id,
      applicationAuthor.authAuthor.firstname,
      applicationAuthor.authAuthor.lastname,
      applicationAuthor.authAuthor.username,
      additionalInfo
    )
  }

}