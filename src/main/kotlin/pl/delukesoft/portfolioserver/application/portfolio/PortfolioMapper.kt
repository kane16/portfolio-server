package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.portfolio.model.*
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe

@Component
@RegisterReflectionForBinding(SkillPortfolioDTO::class, LanguageDTO::class, ProjectDTO::class)
class PortfolioMapper {

  fun mapToDTO(resume: Resume): PortfolioDTO {
    return PortfolioDTO(
      id = resume.id!!,
      fullname = "Łukasz Gumiński",
      imageSource = resume.shortcut.image?.src ?: "",
      title = resume.shortcut.title,
      summary = resume.shortcut.summary,
      skills = resume.skills.map { SkillPortfolioDTO(it.name, it.description, it.level) },
      languages = resume.languages.map { LanguageDTO(it.name, it.level.name) },
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
        mapTimespan(it.timeframe),
        it.skills.map { SkillPortfolioDTO(it.skill.name, it.detail, it.level) },
      )
    }
  }

  private fun mapTimespan(timeframe: Timeframe): TimespanDTO {
    return TimespanDTO(
      "${timeframe.start.year}.${timeframe.start.monthValue.toString().padStart(2, '0')}",
      if (timeframe.end != null) "${timeframe.end.year}.${
        timeframe.end.monthValue.toString().padStart(2, '0')
      }" else "",
    )
  }

}