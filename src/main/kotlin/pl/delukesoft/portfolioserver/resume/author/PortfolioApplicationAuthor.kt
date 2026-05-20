package pl.delukesoft.portfolioserver.resume.author

import pl.delukesoft.authplugin.author.ApplicationAuthor
import pl.delukesoft.authplugin.author.Author

data class PortfolioApplicationAuthor(
    val author: Author,
    val additionalInfo: PortfolioAuthorAdditionalInfo
): ApplicationAuthor {

    override fun getAuthAuthor(): Author {
        return author
    }

    override fun getDomainInfo(): PortfolioAuthorAdditionalInfo {
        return additionalInfo
    }
}
