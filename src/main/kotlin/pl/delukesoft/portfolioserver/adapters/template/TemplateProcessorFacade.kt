package pl.delukesoft.portfolioserver.adapters.template

import org.springframework.stereotype.Component
import org.thymeleaf.context.WebContext
import pl.delukesoft.portfolioserver.application.template.model.PrintDTO
import pl.delukesoft.portfolioserver.domain.generation.DocumentGenerationService
import pl.delukesoft.portfolioserver.domain.resume.read.ResumeService

@Component
class TemplateProcessorFacade(
  private val resumeService: ResumeService,
  private val printMapper: TemplatePrintMapper,
  private val documentGenerationService: DocumentGenerationService,
) {

  fun generateDefaultResumePdf(webContext: WebContext): String {
    val resume = resumeService.getDefaultCV()
    val resumePrint: PrintDTO = printMapper.mapToPrint(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

}