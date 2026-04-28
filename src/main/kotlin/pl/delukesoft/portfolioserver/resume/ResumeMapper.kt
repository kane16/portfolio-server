package pl.delukesoft.portfolioserver.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.security.User
import pl.delukesoft.portfolioserver.resume.language.LanguageDTO
import pl.delukesoft.portfolioserver.resume.history.ResumeHistoryDTO
import pl.delukesoft.portfolioserver.resume.shortcut.ResumeShortcutDTO
import pl.delukesoft.portfolioserver.resume.history.ResumeVersionDTO
import pl.delukesoft.portfolioserver.resume.education.EducationDTO
import pl.delukesoft.portfolioserver.resume.education.InstitutionDTO
import pl.delukesoft.portfolioserver.resume.experience.ExperienceDTO
import pl.delukesoft.portfolioserver.resume.experience.timeframe.TimeframeDTO
import pl.delukesoft.portfolioserver.resume.ResumeEditDTO
import pl.delukesoft.portfolioserver.resume.skill.SkillDTO
import pl.delukesoft.portfolioserver.resume.education.Education
import pl.delukesoft.portfolioserver.resume.education.EducationInstitution
import pl.delukesoft.portfolioserver.resume.education.EducationType
import pl.delukesoft.portfolioserver.resume.experience.Experience
import pl.delukesoft.portfolioserver.resume.experience.business.Business
import pl.delukesoft.portfolioserver.resume.experience.skillexperience.SkillExperience
import pl.delukesoft.portfolioserver.resume.shortcut.ResumeShortcut
import pl.delukesoft.portfolioserver.resume.skill.Skill
import pl.delukesoft.portfolioserver.resume.skill.exception.SkillNotFound
import pl.delukesoft.portfolioserver.resume.timespan.Timeframe
import pl.delukesoft.portfolioserver.resume.history.ResumeHistory
import pl.delukesoft.portfolioserver.resume.history.ResumeVersion

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

  fun mapResumeToEditDTO(resumeVersion: ResumeVersion): ResumeEditDTO {
    val resume = resumeVersion.resume
    return ResumeEditDTO(
      id = resumeVersion.id!!,
      fullname = "${resume.shortcut.user.firstname} ${resume.shortcut.user.lastname}",
      imageSource = resume.shortcut.image?.src ?: "",
      title = resume.shortcut.title,
      summary = resume.shortcut.summary,
      skills = resume.skills.map { SkillDTO(it.name, it.level, "", it.description, it.domains.map { it.name }) },
      languages = resume.languages.map { LanguageDTO(it.name, it.level.name, it.id) },
      sideProjects = mapToExperienceDTO(resume.sideProjects),
      workHistory = mapToExperienceDTO(resume.experience),
      hobbies = resume.hobbies.map { it.name },
      education = resume.education.map { mapEducationToDTO(it) },
      contact = resume.shortcut.contact
    )
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

  fun mapShortcutDTOToResume(shortcut: ResumeShortcutDTO, user: User): ResumeShortcut {
    return ResumeShortcut(
      title = shortcut.title,
      summary = shortcut.summary,
      image = shortcut.image,
      user = user,
      contact = shortcut.contact
    )
  }

  fun mapDTOToExperience(dto: ExperienceDTO, skills: List<Skill>): Experience {
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

  fun mapEducationToDTO(education: Education): EducationDTO {
    return EducationDTO(
      id = education.id,
      title = education.title,
      institution = InstitutionDTO(
        education.institution.name,
        education.institution.city,
        education.institution.country
      ),
      timeframe = TimeframeDTO(education.timeframe.start, education.timeframe.end),
      fieldOfStudy = education.fieldOfStudy,
      grade = education.grade,
      type = (education.type ?: EducationType.DEGREE).name,
      description = education.description,
      externalLinks = education.externalLinks
    )
  }

}