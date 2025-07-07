package pl.delukesoft.portfolioserver.adapters.template

import org.springframework.stereotype.Component
import org.thymeleaf.context.WebContext
import pl.delukesoft.portfolioserver.adapters.auth.UserContext
import pl.delukesoft.portfolioserver.adapters.auth.exception.AuthorizationException
import pl.delukesoft.portfolioserver.application.template.model.PrintDTO
import pl.delukesoft.portfolioserver.domain.generation.DocumentGenerationService
import pl.delukesoft.portfolioserver.domain.resume.read.ResumeService

@Component
class TemplateProcessorFacade(
  private val resumeService: ResumeService,
  private val printMapper: TemplatePrintMapper,
  private val documentGenerationService: DocumentGenerationService,
  private val userContext: UserContext
) {

  fun generateDefaultResumePdf(webContext: WebContext): String {
    val resume = resumeService.getDefaultCV()
    val resumePrint: PrintDTO = printMapper.mapToPrint(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

  fun generateDefaultResumePdfById(webContext: WebContext, id: Long): String {
    val resume = resumeService.getCvById(id)
    val resumePrint: PrintDTO = printMapper.mapToPrint(resume)
    return documentGenerationService.generateResumeHtml(resumePrint, webContext)
  }

}