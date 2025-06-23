package pl.delukesoft.portfolioserver.domain.generation

import org.springframework.stereotype.Service
import org.thymeleaf.context.WebContext
import org.thymeleaf.spring6.SpringTemplateEngine
import pl.delukesoft.portfolioserver.application.template.model.PrintDTO

@Service
class DocumentGenerationService(
  val templateEngine: SpringTemplateEngine
) {

  fun generateResumeHtml(resumePrint: PrintDTO, webContext: WebContext): String {
    resumePrint.attachDataToContext(webContext)
    return templateEngine.process("portfolio-template", webContext)
  }

}