package pl.delukesoft.portfolioserver.pdf

import org.springframework.stereotype.Component
import org.thymeleaf.context.WebContext
import pl.delukesoft.portfolioserver.pdf.model.PrintDTO
import pl.delukesoft.portfolioserver.portfolio.domain.ResumeService

@Component
class GenerationFacade(
  private val resumeService: ResumeService,
  private val printMapper: GenerationPrintMapper,
  private val documentGenerationService: DocumentGenerationService,
) {

  fun generateDefaultResumePdf(webContext: WebContext): String {
    val resume = resumeService.getDefaultCV()
    val resumePrint: PrintDTO = printMapper.mapToPrint(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

}