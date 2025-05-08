package pl.delukesoft.portfolioserver.portfolio

import org.springframework.stereotype.Component
import pl.delukesoft.portfolioserver.portfolio.model.Curriculum

@Component
class CurriculumMapper {

  fun mapToDTO(curriculum: Curriculum): PortfolioDTO {
    return PortfolioDTO(
      id = curriculum.id,
      shortDescription = curriculum.shortDescription,
      dataMatrix = listOf(
        DataMatrixDTO(
          name = "Skills",
          values = curriculum.skills.map { it.name }
        ),
        DataMatrixDTO(
          name = "Experience",
          values = curriculum.experience.map { it.business.name }
        )
      )
    )
  }

}