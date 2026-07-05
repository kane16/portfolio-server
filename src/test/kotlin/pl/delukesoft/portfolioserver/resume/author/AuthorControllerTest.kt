package pl.delukesoft.portfolioserver.resume.author

import tools.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.delukesoft.authplugin.author.ApplicationAuthor
import pl.delukesoft.authplugin.author.AuthorService
import pl.delukesoft.authplugin.security.AuthContext

class AuthorControllerTest {

  private val authorService = mockk<AuthorService>()
  private val authContext = mockk<AuthContext>()
  private val authorMapper = PortfolioAuthorMapper(jacksonObjectMapper(), authContext)
  private val authorController = AuthorController(authorService, authorMapper)

  @Test
  fun `should get all portfolio authors`() {
    val authors = listOf<ApplicationAuthor>(testPortfolioAuthor())
    every { authorService.getAllAuthors("portfolio") } returns authors

    val result = authorController.getAllAuthors()

    assertEquals(authors.map { authorMapper.mapToDto(it) }, result)
    verify(exactly = 1) { authorService.getAllAuthors("portfolio") }
  }

  @Test
  fun `should get current portfolio author`() {
    val author = testPortfolioAuthor()
    every { authorService.getContextAuthor("portfolio") } returns author

    val result = authorController.getContextAuthor("Bearer token")

    assertEquals(authorMapper.mapToDto(author), result)
    verify(exactly = 1) { authorService.getContextAuthor("portfolio") }
  }

  @Test
  fun `should get author by id`() {
    val author = testPortfolioAuthor(id = 7L)
    every { authorService.getAuthorById(7L, "portfolio") } returns author

    val result = authorController.getAuthorById(7L)

    assertEquals(authorMapper.mapToDto(author), result)
    verify(exactly = 1) { authorService.getAuthorById(7L, "portfolio") }
  }

  @Test
  fun `should edit portfolio author`() {
    val author = testPortfolioAuthorRequest()
    val contextAuthor = testPortfolioAuthor()
    val mappedAuthor = contextAuthor.copy(
      additionalInfo = requireNotNull(author.additionalInfo)
    )
    val editedAuthor = testPortfolioAuthor(firstname = "Edited")
    every { authContext.getAuthor() } returns contextAuthor
    every { authorService.editAuthor("portfolio", mappedAuthor) } returns editedAuthor

    val result = authorController.editAuthor(author, "Bearer token")

    assertEquals(authorMapper.mapToDto(editedAuthor), result)
    verify(exactly = 1) { authorService.editAuthor("portfolio", mappedAuthor) }
  }

  @Test
  fun `should reject invalid portfolio author`() {
    val invalidAuthor = PortfolioAuthorRequest(
      AuthorRequest(1L, 0L, "", " ", ""),
      PortfolioAuthorAdditionalInfo()
    )
    val validator = Validation.buildDefaultValidatorFactory().validator

    val violations = validator.validate(invalidAuthor)

    assertEquals(
      setOf(
        "author.userId",
        "author.firstname",
        "author.lastname",
        "author.username"
      ),
      violations.map { it.propertyPath.toString() }.toSet()
    )
  }

  private fun testPortfolioAuthor(
    id: Long = 1L,
    userId: Long = id,
    firstname: String = "Luke",
    lastname: String = "Kane",
    username: String = "kane16"
  ): PortfolioAuthor {
    val additionalInfo = PortfolioAuthorAdditionalInfo()
    return PortfolioAuthor(
      id,
      userId,
      username,
      firstname,
      lastname,
      additionalInfo
    )
  }

  private fun testPortfolioAuthorRequest(
    id: Long = 1L,
    userId: Long = id,
    firstname: String = "Luke",
    lastname: String = "Kane",
    username: String = "kane16"
  ): PortfolioAuthorRequest {
    return PortfolioAuthorRequest(
      AuthorRequest(id, userId, firstname, lastname, username),
      PortfolioAuthorAdditionalInfo()
    )
  }
}
