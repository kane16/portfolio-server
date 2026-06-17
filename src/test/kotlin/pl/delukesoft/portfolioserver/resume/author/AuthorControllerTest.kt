package pl.delukesoft.portfolioserver.resume.author

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import pl.delukesoft.authplugin.author.Author
import pl.delukesoft.authplugin.author.AuthorService

class AuthorControllerTest {

  private val authorService = mockk<AuthorService>()
  private val authorMapper = PortfolioAuthorMapper(ObjectMapper())
  private val authorController = AuthorController(authorService, authorMapper)

  @Test
  fun `should get all portfolio authors`() {
    val authors = listOf(testAuthor())
    every { authorService.getAllAuthors("portfolio") } returns authors

    val result = authorController.getAllAuthors("Bearer token")

    assertSame(authors, result)
    verify(exactly = 1) { authorService.getAllAuthors("portfolio") }
  }

  @Test
  fun `should get current portfolio author`() {
    val author = testPortfolioAuthor()
    every { authorService.getContextAuthor("portfolio") } returns author

    val result = authorController.getContextAuthor("Bearer token")

    assertSame(author, result)
    verify(exactly = 1) { authorService.getContextAuthor("portfolio") }
  }

  @Test
  fun `should get author by id`() {
    val author = testAuthor(id = 7L)
    every { authorService.getAuthorById(7L) } returns author

    val result = authorController.getAuthorById(7L, "Bearer token")

    assertSame(author, result)
    verify(exactly = 1) { authorService.getAuthorById(7L) }
  }

  @Test
  fun `should edit portfolio author`() {
    val author = testPortfolioAuthorRequest()
    val mappedAuthor = authorMapper.mapToApplicationAuthor(author)
    val editedAuthor = testPortfolioAuthor(firstname = "Edited")
    every { authorService.editAuthor("portfolio", mappedAuthor) } returns editedAuthor

    val result = authorController.editAuthor(author, "Bearer token")

    assertSame(editedAuthor, result)
    verify(exactly = 1) { authorService.editAuthor("portfolio", mappedAuthor) }
  }

  @Test
  fun `should delete author by id`() {
    every { authorService.deleteAuthor(7L) } just runs

    authorController.deleteAuthor(7L, "Bearer token")

    verify(exactly = 1) { authorService.deleteAuthor(7L) }
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

  private fun testAuthor(
    id: Long = 1L,
    userId: Long = id,
    firstname: String = "Luke",
    lastname: String = "Kane",
    username: String = "kane16"
  ): Author {
    return Author(id, userId, firstname, lastname, username, null)
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
      Author(
        id,
        userId,
        firstname,
        lastname,
        username,
        ObjectMapper().valueToTree(additionalInfo)
      ),
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
