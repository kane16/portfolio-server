package pl.delukesoft.portfolioserver.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.portfolio.domain.model.Resume
import pl.delukesoft.portfolioserver.portfolio.model.PortfolioInfoDTO
import pl.delukesoft.portfolioserver.portfolio.model.PortfolioDTO
import pl.delukesoft.portfolioserver.portfolio.model.PortfolioInfoEntryDTO

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
          PortfolioInfoEntryDTO(it.name, it.level.name)
        }
      ),
    )
  }

}