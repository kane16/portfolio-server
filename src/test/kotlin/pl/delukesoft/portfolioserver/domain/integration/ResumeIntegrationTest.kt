package pl.delukesoft.portfolioserver.domain.integration

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import pl.delukesoft.blog.image.exception.ResumeExistsException
import pl.delukesoft.blog.image.exception.ResumeNotFound
import pl.delukesoft.portfolioserver.TestcontainersConfiguration
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.ResumeRepository
import pl.delukesoft.portfolioserver.domain.resume.ResumeService
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistoryRepository
import pl.delukesoft.portfolioserver.utility.DateTimeUtils
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration::class)
class ResumeIntegrationTest {

  @Autowired
  private lateinit var resumeService: ResumeService

  @Autowired
  private lateinit var resumeRepository: ResumeRepository

  @Autowired
  private lateinit var resumeHistoryRepository: ResumeHistoryRepository

  val adminUser = User(
    email = "john.doe@example.com",
    username = "admin",
    roles = listOf("ROLE_USER", "ROLE_CANDIDATE", "ROLE_ADMIN")
  )

  @AfterEach
  fun tearDown() {
    resumeHistoryRepository.deleteAll()
    resumeRepository.deleteAll()
  }

  @Test
  fun `Add resume when not exists and retrieve it by ID`() {
    val providedResume = resumeService.addResume(
      Resume(
        shortcut = ResumeShortcut(
          title = "Test resume",
          summary = "Test summary",
          image = null,
          user = adminUser
        )
      )
    )
    val providedResumeId = providedResume.id
    assertNotNull(providedResumeId)
    val dbResume = resumeService.getCvById(providedResumeId, adminUser)
    assertEquals(dbResume.skills, emptyList())
    assertEquals(providedResume.shortcut.title, dbResume.shortcut.title)
    assertEquals(providedResume.shortcut.summary, dbResume.shortcut.summary)
    assertEquals(dbResume.experience, emptyList())
    assertEquals(dbResume.sideProjects, emptyList())
    assertEquals(dbResume.hobbies, emptyList())
    assertEquals(dbResume.languages, emptyList())
    DateTimeUtils.assertEqualsDateTimes(dbResume.createdOn, providedResume.createdOn)
    DateTimeUtils.assertEqualsDateTimes(dbResume.lastModified, providedResume.lastModified)
  }

  @Test
  fun `Prevent add resume when exists and throw Exception`() {
    val providedResume = resumeService.addResume(
      Resume(
        shortcut = ResumeShortcut(
          title = "Test resume",
          summary = "Test summary",
          user = adminUser
        ),
      )
    )
    assertThrows<ResumeExistsException> {
      resumeService.addResume(
        Resume(
          id = providedResume.id,
          shortcut = ResumeShortcut(
            title = "Test resume",
            summary = "Test summary",
            user = adminUser
          ),
          skills = emptyList(),
          experience = emptyList(),
          sideProjects = emptyList(),
          hobbies = emptyList(),
          languages = emptyList()
        )
      )
    }
  }

  @Test
  fun `Update of saved resume should be successful`() {
    val providedResume = resumeService.addResume(
      Resume(
        shortcut = ResumeShortcut(
          title = "Test resume",
          summary = "Test summary",
          user = adminUser
        )
      )
    )
    val updatedResume = resumeService.updateResume(
      providedResume.copy(
        shortcut = ResumeShortcut(
          title = "Updated title",
          summary = "Updated summary",
          user = adminUser
        )
      )
    )
    val dbResume = resumeService.getCvById(updatedResume.id!!, adminUser)
    assertEquals(dbResume.skills, emptyList())
    Assertions.assertNotEquals(providedResume.shortcut.title, dbResume.shortcut.title)
    Assertions.assertNotEquals(providedResume.shortcut.summary, dbResume.shortcut.summary)
    Assertions.assertEquals(updatedResume.shortcut.title, "Updated title")
    Assertions.assertEquals(updatedResume.shortcut.summary, "Updated summary")
    assertEquals(dbResume.experience, emptyList())
    assertEquals(dbResume.sideProjects, emptyList())
    assertEquals(dbResume.hobbies, emptyList())
    assertEquals(dbResume.languages, emptyList())
    DateTimeUtils.assertEqualsDateTimes(dbResume.createdOn, providedResume.createdOn)
    DateTimeUtils.assertNotEqualsDateTimes(dbResume.lastModified, providedResume.lastModified)
  }

  @Test
  fun `Update of resume should fail if resume does not exist`() {
    assertThrows<ResumeNotFound> {
      resumeService.updateResume(
        Resume(
          id = 20L,
          shortcut = ResumeShortcut(
            title = "Test resume",
            summary = "Test summary",
            user = adminUser
          )
        )
      )
    }
  }

}