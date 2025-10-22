package pl.delukesoft.portfolioserver.application.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.application.portfolio.model.*
import pl.delukesoft.portfolioserver.application.resume.education.EducationDTO
import pl.delukesoft.portfolioserver.application.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.application.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.application.resume.model.ResumeEditDTO
import pl.delukesoft.portfolioserver.application.resume.skill.SkillDTO
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.education.Education
import pl.delukesoft.portfolioserver.domain.resume.education.EducationInstitution
import pl.delukesoft.portfolioserver.domain.resume.education.EducationType
import pl.delukesoft.portfolioserver.domain.resume.experience.Experience
import pl.delukesoft.portfolioserver.domain.resume.experience.business.Business
import pl.delukesoft.portfolioserver.domain.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.domain.resume.skill.Skill
import pl.delukesoft.portfolioserver.domain.resume.skill.exception.SkillNotFound
import pl.delukesoft.portfolioserver.domain.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeHistory
import pl.delukesoft.portfolioserver.domain.resumehistory.ResumeVersion

@Component
class ResumeMapper {

  fun mapHistoryToDTO(history: ResumeHistory): ResumeHistoryDTO {
    return ResumeHistoryDTO(
      if (history.defaultResume != null) mapVersionToDTO(history.defaultResume) else null,
      history.versions.map { mapVersionToDTO(it) }
    )
  }

  fun mapVersionToDTO(version: ResumeVersion): ResumeVersionDTO {
    return ResumeVersionDTO(
      version.id!!,
      version.resume.shortcut.title,
      version.resume.shortcut.summary,
      version.version,
      version.state.name
    )
  }

  fun mapResumeToEditDTO(resume: Resume): ResumeEditDTO {
    return ResumeEditDTO(
      id = resume.id!!,
      fullname = "Łukasz Gumiński",
      imageSource = resume.shortcut.image?.src ?: "",
      title = resume.shortcut.title,
      summary = resume.shortcut.summary,
      skills = resume.skills.map { SkillDTO(it.name, it.level, "", it.description, it.domains.map { it.name }) },
      languages = resume.languages.map { LanguageDTO(it.name, it.level.name, it.id) },
      sideProjects = mapToExperienceDTO(resume.sideProjects),
      workHistory = mapToExperienceDTO(resume.experience),
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
        it.skills.map { SkillPortfolioDTO(it.skill.name, it.detail) },
      )
    }
  }

  private fun mapToExperienceDTO(experiences: List<Experience>): List<ExperienceDTO> {
    return experiences.map {
      ExperienceDTO(
        it.id,
        it.business.name,
        it.position,
        it.summary,
        it.description,
        TimeframeDTO(it.timeframe.start, it.timeframe.end),
        it.skills.map {
          SkillDTO(
            it.skill.name,
            it.level,
            it.detail,
            it.skill.description,
            it.skill.domains.map { it.name })
        }
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

  fun mapShortcutDTOToResume(shortcut: ResumeShortcutDTO, user: User): ResumeShortcut {
    return ResumeShortcut(
      title = shortcut.title,
      summary = shortcut.summary,
      image = shortcut.image,
      user = user
    )
  }

  fun mapExperienceDTOToResume(dto: ExperienceDTO, skills: List<Skill>): Experience {
    return Experience(
      dto.id,
      Business(dto.business),
      dto.position,
      dto.summary,
      dto.description,
      Timeframe(dto.timespan.start, dto.timespan.end),
      dto.skills.map { skill ->
        SkillExperience(
          skills.find { it.name == skill.name } ?: throw SkillNotFound(skill.name),
          skill.level,
          skill.detail ?: ""
        )
      }
    )
  }

  fun mapDTOToEducation(educationDTO: EducationDTO): Education {
    return Education(
      id = null,
      title = educationDTO.title,
      institution = EducationInstitution(
        educationDTO.institution.name,
        educationDTO.institution.city,
        educationDTO.institution.country
      ),
      timeframe = Timeframe(educationDTO.timeframe.start, educationDTO.timeframe.end),
      fieldOfStudy = educationDTO.fieldOfStudy,
      grade = educationDTO.grade,
      type = EducationType.valueOf(educationDTO.type),
      description = educationDTO.description,
      externalLinks = educationDTO.externalLinks
    )
  }

}