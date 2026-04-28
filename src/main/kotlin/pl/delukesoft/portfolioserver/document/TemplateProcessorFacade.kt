package pl.delukesoft.portfolioserver.document

import org.springframework.stereotype.Component
import org.thymeleaf.context.WebContext
import pl.delukesoft.portfolioserver.document.DocumentGenerationService
import pl.delukesoft.portfolioserver.document.PrintDTO
import pl.delukesoft.portfolioserver.portfolio.PortfolioMapper
import pl.delukesoft.portfolioserver.resume.ResumeFacade

@Component
class TemplateProcessorFacade(
  private val resumeFacade: ResumeFacade,
  private val printMapper: PortfolioMapper,
  private val documentGenerationService: DocumentGenerationService
) {

  fun generateDefaultResumePdf(webContext: WebContext): String {
    val resume = resumeFacade.getDefaultCV()
    val resumePrint: PrintDTO = printMapper.mapToDTO(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

  fun generateDefaultResumePdfById(webContext: WebContext, id: Long): String {
    val resume = resumeFacade.getById(id)
    val resumePrint: PrintDTO = printMapper.mapToDTO(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

}