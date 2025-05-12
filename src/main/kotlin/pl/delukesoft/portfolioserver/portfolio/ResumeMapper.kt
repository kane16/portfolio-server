package pl.delukesoft.portfolioserver.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.portfolio.model.Resume

@Component
class ResumeMapper {

  fun mapToDTO(resume: Resume): PortfolioDTO {
    return PortfolioDTO(
      id = resume.id,
      shortDescription = resume.shortDescription,
      dataMatrix = listOf(
        DataMatrixDTO(
          name = "Skills",
          values = resume.skills.map { it.name }
        ),
        DataMatrixDTO(
          name = "Experience",
          values = resume.experience.map { it.business.name }
        )
      )
    )
  }

}