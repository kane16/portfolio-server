package pl.delukesoft.portfolioserver.portfolio.generator

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.portfolio.generator.model.PrintDTO
import pl.delukesoft.portfolioserver.portfolio.generator.model.ProjectDTO
import pl.delukesoft.portfolioserver.portfolio.generator.model.ResumePrintDTO
import pl.delukesoft.portfolioserver.portfolio.generator.model.TimespanDTO
import pl.delukesoft.portfolioserver.portfolio.model.Experience
import pl.delukesoft.portfolioserver.portfolio.model.Resume
import pl.delukesoft.portfolioserver.portfolio.model.Timespan
import java.time.format.DateTimeFormatter

@Component
class GenerationPrintMapper {

  fun mapToPrint(resume: Resume): PrintDTO{
    return ResumePrintDTO(
      fullname = "Łukasz Gumiński",
      imageSource = resume.image?.src ?: "",
      title = resume.title,
      summary = resume.summary,
      skills = resume.skills.map { it.name },
      languages = resume.languages.map { it.name },
      projects = emptyList(),
      workHistory = mapToProjects(resume.experience),
      hobbies = resume.hobbies
    )
  }

  private fun mapToProjects(experience: List<Experience>): List<ProjectDTO> {
    return experience.map {
      ProjectDTO(
        it.business.name,
        mapTimespan(it.timespan),
        it.summary,
        it.skills.map { it.name },
      )
    }
  }

  private fun mapTimespan(timespan: Timespan): TimespanDTO {
    return TimespanDTO(
      timespan.start.format(DateTimeFormatter.ISO_LOCAL_DATE),
      timespan.end?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "",
    )
  }

}