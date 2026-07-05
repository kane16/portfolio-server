package pl.delukesoft.portfolioserver.resume.author

import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.convertValue
import org.springframework.stereotype.Component
import pl.delukesoft.authplugin.author.ApplicationAuthor
import pl.delukesoft.authplugin.author.Author
import pl.delukesoft.authplugin.author.AuthorMapper
import pl.delukesoft.authplugin.security.AuthContext

@Component
class PortfolioAuthorMapper(
  val objectMapper: ObjectMapper,
  val authContext: AuthContext
) : AuthorMapper {

  override fun mapToApplicationAuthor(author: Author): ApplicationAuthor {
    val additionalInfo = if(author.additionalInfo != null && !author.additionalInfo.isNull)
      objectMapper.convertValue<PortfolioAuthorAdditionalInfo>(author.additionalInfo)
    else PortfolioAuthorAdditionalInfo()

    return PortfolioAuthor(
      id = author.id,
      userId = author.userId,
      username = author.username,
      firstname = author.firstname,
      lastname = author.lastname,
      additionalInfo = additionalInfo
    )
  }

  override fun mapToAuthAuthor(appAuthor: ApplicationAuthor): Author {
    return Author(
      appAuthor.authorId,
      appAuthor.authorUserId,
      appAuthor.authorFirstname,
      appAuthor.authorLastname,
      appAuthor.authorUsername,
      objectMapper.convertValue(appAuthor.domainInfo)
    )
  }

  fun mapToDto(appAuthor: ApplicationAuthor): PortfolioAuthorDTO {
    return PortfolioAuthorDTO(
      appAuthor.authorId,
      appAuthor.authorUserId,
      appAuthor.authorFirstname,
      appAuthor.authorLastname,
      appAuthor.authorUsername,
      appAuthor.domainInfo as PortfolioAuthorAdditionalInfo
    )
  }

  fun mapFromRequestToDomainAuthor(authorRequest: PortfolioAuthorRequest): PortfolioAuthor {
    val contextAuthor = authContext.getAuthor()
    return PortfolioAuthor(
      id = contextAuthor.authorId,
      userId = contextAuthor.authorUserId,
      username = contextAuthor.authorUsername,
      firstname = contextAuthor.authorFirstname,
      lastname = contextAuthor.authorLastname,
      additionalInfo = objectMapper.convertValue(authorRequest.additionalInfo)
    )
  }


}
