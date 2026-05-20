package pl.delukesoft.portfolioserver.resume.author

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.author.ApplicationAuthor
import pl.delukesoft.authplugin.author.Author
import pl.delukesoft.authplugin.author.AuthorMapper

@Component
class PortfolioAuthorMapper(
    val objectMapper: ObjectMapper
): AuthorMapper {

    override fun mapToApplicationAuthor(author: Author): PortfolioApplicationAuthor {
        val additionalInfo = objectMapper.convertValue<PortfolioAuthorAdditionalInfo>(author.additionalInfo)
        return PortfolioApplicationAuthor(
            author,
            additionalInfo
        )
    }

    override fun mapToAuthAuthor(applicationAuthor: ApplicationAuthor): Author {
        val additionalInfo = objectMapper.convertValue<JsonNode>(applicationAuthor.domainInfo)
        return Author(
            applicationAuthor.authAuthor.userId,
            applicationAuthor.authAuthor.firstname,
            applicationAuthor.authAuthor.lastname,
            applicationAuthor.authAuthor.username,
            additionalInfo
        )
    }

}