package pl.delukesoft.portfolioserver.domain.resume

import org.junit.jupiter.api.BeforeEach
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
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.hobby.Hobby
import pl.delukesoft.portfolioserver.domain.resume.language.Language
import pl.delukesoft.portfolioserver.domain.resume.language.LanguageLevel
import pl.delukesoft.portfolioserver.domain.resume.language.WorkLanguage
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.utility.DateTimeUtils.assertEqualsDateTimes
import pl.delukesoft.portfolioserver.utility.DateTimeUtils.assertNotEqualsDateTimes
import java.time.LocalDate
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

  val adminUser = User(
    email = "admin@sa.mm",
    username = "admin",
    roles = listOf("ROLE_ADMIN")
  )

  @Test
  fun `Add resume when not exists and retrieve it by ID`() {
    val providedResume = resumeService.addResume(
      Resume(
        title = "Test resume",
        summary = "Test summary",
        skills = listOf(
          Skill(
            id = 1L,
            name = "Skill 1",
            level = 5
          )
        ),
        experience = listOf(
          Experience(
            id = 1,
            business = Business(
              name = "12"
            ),
            position = "dsf",
            summary = "sdf",
            timespan = Timespan(
              LocalDate.now(),
              LocalDate.now()
            ),
            skills = emptyList()
          )
        ),
        sideProjects = listOf(
          Experience(
            id = 1,
            business = Business(
              name = "12"
            ),
            position = "dsf",
            summary = "sdf",
            timespan = Timespan(
              LocalDate.now(),
              LocalDate.now()
            ),
            skills = emptyList()
          )
        ),
        hobbies = listOf(
          Hobby(
            name = "Test hobby",
          )
        ),
        languages = listOf(
          WorkLanguage(
            language = Language(
              name = "Test language"
            ),
            level = LanguageLevel.A1
          )
        )
      )
    )
    assertNotNull(providedResume.id)
    val dbResume = resumeService.getCvById(providedResume.id!!, adminUser)
    assertEquals(dbResume.skills, emptyList())
    assertEquals(providedResume.title, dbResume.title)
    assertEquals(providedResume.summary, dbResume.summary)
    assertEquals(dbResume.experience, emptyList())
    assertEquals(dbResume.sideProjects, emptyList())
    assertEquals(dbResume.hobbies, emptyList())
    assertEquals(dbResume.languages, emptyList())
    assertEqualsDateTimes(dbResume.createdOn, providedResume.createdOn)
    assertEqualsDateTimes(dbResume.lastModified, providedResume.lastModified)
    resumeRepository.deleteById(dbResume.id!!)
  }

  @Test
  fun `Prevent add resume when exists and throw Exception`() {
    assertThrows<ResumeExistsException> {
      resumeService.addResume(
        Resume(
          id = 1L,
          title = "Test resume",
          summary = "Test summary",
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
        title = "Test resume",
        summary = "Test summary",
        skills = emptyList(),
        experience = emptyList(),
        sideProjects = emptyList(),
        hobbies = emptyList(),
        languages = emptyList()
      )
    )
    val updatedResume = resumeService.updateResume(
      providedResume.copy(
        title = "Updated title",
        summary = "Updated summary",
        skills = listOf(
          Skill(
            id = 1L,
            name = "Skill 1",
            level = 5
          )
        ),
        experience = listOf(
          Experience(
            id = 1,
            business = Business(
              name = "12"
            ),
            position = "dsf",
            summary = "sdf",
            timespan = Timespan(
              LocalDate.now(),
              LocalDate.now()
            ),
            skills = emptyList()
          )
        ),
        sideProjects = listOf(
          Experience(
            id = 1,
            business = Business(
              name = "12"
            ),
            position = "dsf",
            summary = "sdf",
            timespan = Timespan(
              LocalDate.now(),
              LocalDate.now()
            ),
            skills = emptyList()
          )
        ),
        hobbies = listOf(
          Hobby(
            name = "Test hobby",
          )
        ),
        languages = listOf(
          WorkLanguage(
            language = Language(
              name = "Test language"
            ),
            level = LanguageLevel.A1
          )
        )
      )
    )
    val dbResume = resumeService.getCvById(updatedResume.id!!, adminUser)
    assertEquals(dbResume.skills, emptyList())
    Assertions.assertNotEquals(providedResume.title, dbResume.title)
    Assertions.assertNotEquals(providedResume.summary, dbResume.summary)
    Assertions.assertEquals(updatedResume.title, "Updated title")
    Assertions.assertEquals(updatedResume.summary, "Updated summary")
    assertEquals(dbResume.experience, emptyList())
    assertEquals(dbResume.sideProjects, emptyList())
    assertEquals(dbResume.hobbies, emptyList())
    assertEquals(dbResume.languages, emptyList())
    assertEqualsDateTimes(dbResume.createdOn, providedResume.createdOn)
    assertNotEqualsDateTimes(dbResume.lastModified, providedResume.lastModified)
    resumeRepository.deleteById(dbResume.id!!)
  }

  @Test
  fun `Update of resume should fail if resume does not exist`() {
    assertThrows<ResumeNotFound> {
      resumeService.updateResume(
        Resume(
          id = 20L,
          title = "Test resume",
          summary = "Test summary",
          skills = emptyList(),
          experience = emptyList(),
          sideProjects = emptyList(),
          hobbies = emptyList(),
          languages = emptyList()
        )
      )
    }
  }

}