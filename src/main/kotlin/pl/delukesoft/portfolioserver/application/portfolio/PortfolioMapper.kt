package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.pdf.model.PrintDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.LanguageDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ProjectDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.SkillDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.TimespanDTO
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.Timespan
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience

@Component
@RegisterReflectionForBinding(SkillDTO::class, LanguageDTO::class, ProjectDTO::class)
class PortfolioMapper {

  fun mapToDTO(resume: Resume): PortfolioDTO {
    return PortfolioDTO(
      id = resume.id!!,
      fullname = "Łukasz Gumiński",
      imageSource = resume.image?.src ?: "",
      title = resume.title,
      summary = resume.summary,
      skills = resume.skills.map { SkillDTO(it.name, it.description, it.level) },
      languages = resume.languages.map { LanguageDTO(it.language.name, it.level.name) },
      sideProjects = mapToProjects(resume.sideProjects),
      workHistory = mapToProjects(resume.experience),
      hobbies = resume.hobbies.map { it.name },
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
        it.skills.map { SkillDTO(it.skill.name, it.detail, it.level) },
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