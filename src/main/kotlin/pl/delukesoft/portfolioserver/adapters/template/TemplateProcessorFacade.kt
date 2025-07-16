package pl.delukesoft.portfolioserver.adapters.template

import org.springframework.stereotype.Component
import org.thymeleaf.context.WebContext
import pl.delukesoft.portfolioserver.adapters.resume.PortfolioSearch
import pl.delukesoft.portfolioserver.adapters.resume.ResumeFacade
import pl.delukesoft.portfolioserver.adapters.template.model.PrintDTO
import pl.delukesoft.portfolioserver.domain.resumehistory.resume.print.DocumentGenerationService

@Component
class TemplateProcessorFacade(
  private val resumeFacade: ResumeFacade,
  private val printMapper: TemplatePrintMapper,
  private val documentGenerationService: DocumentGenerationService
) {

  fun generateDefaultResumePdf(webContext: WebContext, portfolioSearch: PortfolioSearch? = null): String {
    val resume = resumeFacade.getDefaultCV(portfolioSearch)
    val resumePrint: PrintDTO = printMapper.mapToPrint(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

  fun generateDefaultResumePdfById(webContext: WebContext, id: Long, portfolioSearch: PortfolioSearch? = null): String {
    val resume = resumeFacade.getCvById(id, portfolioSearch)
    val resumePrint: PrintDTO = printMapper.mapToPrint(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

}