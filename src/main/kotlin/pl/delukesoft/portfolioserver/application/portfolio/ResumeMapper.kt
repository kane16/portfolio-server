package pl.delukesoft.portfolioserver.application.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioInfoDTO
import pl.delukesoft.portfolioserver.application.portfolio.model.PortfolioInfoEntryDTO
import pl.delukesoft.portfolioserver.domain.resume.Resume

@Component
class ResumeMapper {

  fun mapToDTO(resume: Resume): PortfolioDTO {
    return PortfolioDTO(
      id = resume.id,
      title = resume.title,
      description = resume.summary,
      skills = PortfolioInfoDTO(
        name = "Skills",
        values = resume.skills.map {
          PortfolioInfoEntryDTO(it.name, it.description ?: "")
        }
      ),
      experience = PortfolioInfoDTO(
        name = "Experience",
        values = resume.experience.map {
          PortfolioInfoEntryDTO(it.position, it.description ?: "")
        }
      ),
      business = PortfolioInfoDTO(
        name = "Business",
        values = resume.experience.map {
          PortfolioInfoEntryDTO(it.business.name, it.description ?: "")
        }
      ),
      languages = PortfolioInfoDTO(
        name = "Languages",
        values = resume.languages.map {
          PortfolioInfoEntryDTO(it.language.name, it.level.name)
        }
      ),
    )
  }

}