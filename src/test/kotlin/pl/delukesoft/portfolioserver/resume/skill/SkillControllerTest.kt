package pl.delukesoft.portfolioserver.resume.skill

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.portfolioserver.resume.ResumeFacade
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthor
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthorAdditionalInfo
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthorMapper

class SkillControllerTest {

  private val skillFacade = mockk<SkillFacade>()
  private val resumeFacade = mockk<ResumeFacade>()
  private val authorMapper = PortfolioAuthorMapper(ObjectMapper(), mockk<AuthContext>())
  private val skillController = SkillController(skillFacade, resumeFacade, authorMapper)

  @Test
  fun `should return DTO after adding a skill domain`() {
    val additionalInfo = PortfolioAuthorAdditionalInfo()
    val author = PortfolioAuthor(
      1L,
      1L,
      "kane16",
      "Luke",
      "Kane",
      additionalInfo
    )
    every { skillFacade.addDomain("backend") } returns author

    val result = skillController.addSkillDomain("backend", "Bearer token")

    assertEquals(authorMapper.mapToDto(author), result)
    verify(exactly = 1) { skillFacade.addDomain("backend") }
  }
}
