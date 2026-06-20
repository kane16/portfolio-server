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

  fun mapToApplicationAuthor(author: PortfolioAuthorRequest): PortfolioAuthor {
    val requestAuthor = requireNotNull(author.author)
    val additionalInfo = requireNotNull(author.additionalInfo)
    return PortfolioAuthor(
      Author(
        requestAuthor.id,
        requireNotNull(requestAuthor.userId),
        requireNotNull(requestAuthor.firstname),
        requireNotNull(requestAuthor.lastname),
        requireNotNull(requestAuthor.username),
        objectMapper.valueToTree(additionalInfo)
      ),
      additionalInfo
    )
  }

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

  fun mapToDto(author: Author): PortfolioAuthorDTO {
    val additionalInfo = author.additionalInfo
      ?.takeUnless { it.isNull }
      ?.let { objectMapper.convertValue<PortfolioAuthorAdditionalInfo>(it) }
    return PortfolioAuthorDTO(
      author.id,
      author.userId,
      author.firstname,
      author.lastname,
      author.username,
      additionalInfo
    )
  }

  fun mapToDto(author: PortfolioAuthor): PortfolioAuthorDTO {
    return PortfolioAuthorDTO(
      author.authAuthor.id,
      author.authAuthor.userId,
      author.authAuthor.firstname,
      author.authAuthor.lastname,
      author.authAuthor.username,
      author.additionalInfo
    )
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
