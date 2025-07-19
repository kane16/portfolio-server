package pl.delukesoft.portfolioserver.application.pdf

import org.springframework.stereotype.Component
import org.thymeleaf.context.WebContext
import pl.delukesoft.portfolioserver.adapters.print.DocumentGenerationService
import pl.delukesoft.portfolioserver.application.pdf.model.PrintDTO
import pl.delukesoft.portfolioserver.application.portfolio.PortfolioMapper
import pl.delukesoft.portfolioserver.application.filter.PortfolioSearch
import pl.delukesoft.portfolioserver.domain.resume.ResumeFacade

@Component
class TemplateProcessorFacade(
  private val resumeFacade: ResumeFacade,
  private val printMapper: PortfolioMapper,
  private val documentGenerationService: DocumentGenerationService
) {

  fun generateDefaultResumePdf(webContext: WebContext, portfolioSearch: PortfolioSearch? = null): String {
    val resume = resumeFacade.getDefaultCV(portfolioSearch)
    val resumePrint: PrintDTO = printMapper.mapToDTO(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

  fun generateDefaultResumePdfById(webContext: WebContext, id: Long, portfolioSearch: PortfolioSearch? = null): String {
    val resume = resumeFacade.getCvById(id, portfolioSearch)
    val resumePrint: PrintDTO = printMapper.mapToDTO(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

}