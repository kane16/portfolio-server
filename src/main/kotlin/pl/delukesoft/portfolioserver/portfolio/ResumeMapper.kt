package pl.delukesoft.portfolioserver.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.portfolio.model.Resume

@Component
class ResumeMapper {

  fun mapToDTO(resume: Resume): PortfolioDTO {
    return PortfolioDTO(
      id = resume.id,
      title = resume.title,
      description = resume.summary,
      skills = DataMatrixDTO(
        name = "Skills",
        values = resume.skills.map { it.name }
      ),
      experience = DataMatrixDTO(
        name = "Experience",
        values = resume.experience.map { it.position }
      ),
      business = DataMatrixDTO(
        name = "Business",
        values = resume.experience.map { it.business.name }
      ),
      languages = DataMatrixDTO(
        name = "Languages",
        values = resume.languages.map { it.name }
      ),
    )
  }

}