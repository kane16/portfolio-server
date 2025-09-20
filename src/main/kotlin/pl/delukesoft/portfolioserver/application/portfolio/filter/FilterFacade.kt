package pl.delukesoft.portfolioserver.application.portfolio.filter

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.domain.user.AuthorService

@Component
class FilterFacade(
  private val authorService: AuthorService,
  private val userContext: UserContext
) {

  fun getAllSkills(): List<String> {
    val author = if (userContext.author == null) {
      authorService.getMainAuthor()
    } else userContext.author!!
    return author.skills.map { it.name }
  }

  fun getAllBusiness(): List<String> {
    val author = if (userContext.author == null) {
      authorService.getMainAuthor()
    } else userContext.author!!
    return author.businesses.map { it.name }
  }

  fun getAllDomains(): List<String> {
    val author = if (userContext.author == null) {
      authorService.getMainAuthor()
    } else userContext.author!!
    return author.domains.map { it.name }
  }

  fun getAllFilters(): PortfolioSearch {
    return PortfolioSearch(
      getAllBusiness(),
      getAllSkills(),
      getAllDomains()
    )
  }

}