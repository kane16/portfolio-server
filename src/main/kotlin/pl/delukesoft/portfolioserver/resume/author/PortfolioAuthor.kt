package pl.delukesoft.portfolioserver.resume.author

import pl.delukesoft.authplugin.author.ApplicationAuthor

data class PortfolioAuthor(
  val id: Long,
  val userId: Long,
  val username: String,
  val firstname: String,
  val lastname: String,
  val additionalInfo: PortfolioAuthorAdditionalInfo
) : ApplicationAuthor {

  override fun getAuthorId(): Long? {
    return id
  }

  override fun getAuthorUserId(): Long? {
    return userId
  }

  override fun getAuthorUsername(): String? {
    return username
  }

  override fun getAuthorFirstname(): String? {
    return firstname
  }

  override fun getAuthorLastname(): String? {
    return lastname
  }

  override fun getDomainInfo(): PortfolioAuthorAdditionalInfo {
    return additionalInfo
  }
}
