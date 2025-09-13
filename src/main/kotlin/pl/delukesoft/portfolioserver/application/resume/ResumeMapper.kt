package pl.delukesoft.portfolioserver.application.resume

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.adapters.auth.User
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeHistoryDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeShortcutDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.ResumeVersionDTO
import pl.delukesoft.portfolioserver.domain.resume.Resume
import pl.delukesoft.portfolioserver.domain.resume.shortcut.ResumeShortcut
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

  fun mapShortcutDTOToResume(shortcut: ResumeShortcutDTO, user: User, resumeId: Long? = null): Resume {
    return Resume(
      id = resumeId,
      shortcut = ResumeShortcut(
        title = shortcut.title,
        summary = shortcut.summary,
        image = shortcut.image,
        user = user
      )
    )
  }

}