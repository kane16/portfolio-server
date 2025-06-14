package pl.delukesoft.portfolioserver.pdf

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.pdf.model.LanguageDTO
import pl.delukesoft.portfolioserver.pdf.model.PrintDTO
import pl.delukesoft.portfolioserver.pdf.model.ProjectDTO
import pl.delukesoft.portfolioserver.pdf.model.ResumePrintDTO
import pl.delukesoft.portfolioserver.pdf.model.SkillDTO
import pl.delukesoft.portfolioserver.pdf.model.TimespanDTO
import pl.delukesoft.portfolioserver.portfolio.model.Experience
import pl.delukesoft.portfolioserver.portfolio.model.Resume
import pl.delukesoft.portfolioserver.portfolio.model.Timespan

@Component
class GenerationPrintMapper {

  fun mapToPrint(resume: Resume): PrintDTO {
    return ResumePrintDTO(
      fullname = "Łukasz Gumiński",
      imageSource = resume.image?.src ?: "",
      title = resume.title,
      summary = resume.summary,
      skills = resume.skills.map { SkillDTO(it.name, it.description, it.level) },
      languages = resume.languages.map { LanguageDTO(it.name, it.level.name) },
      projects = emptyList(),
      workHistory = mapToProjects(resume.experience),
      hobbies = resume.hobbies
    )
  }

  private fun mapToProjects(experience: List<Experience>): List<ProjectDTO> {
    return experience.map {
      ProjectDTO(
        it.position,
        it.business.name,
        it.summary,
        it.description ?: "",
        mapTimespan(it.timespan),
        it.skills.map { SkillDTO(it.name, it.description, it.level) },
      )
    }
  }

  private fun mapTimespan(timespan: Timespan): TimespanDTO {
    return TimespanDTO(
      "${timespan.start.year}.${timespan.start.monthValue.toString().padStart(2, '0')}",
      if (timespan.end != null) "${timespan.end.year}.${timespan.end.monthValue.toString().padStart(2, '0')}" else "",
    )
  }

}