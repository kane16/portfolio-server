package pl.delukesoft.portfolioserver.application.pdf

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import org.thymeleaf.context.WebContext
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@RequestMapping("/pdf")
class TemplateProcessorController(
  val templateProcessorFacade: TemplateProcessorFacade
) {

  @AuthRequired(anonymousAllowed = true)
  @GetMapping(produces = ["text/html"])
  fun generatePDF(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @RequestHeader("Authorization") token: String? = null
  ): String {
    val webApplication = JakartaServletWebApplication.buildApplication(request.servletContext)
    val webContext = WebContext(webApplication.buildExchange(request, response), request.locale)
    return templateProcessorFacade.generateDefaultResumePdf(
      webContext
    )
  }

  @AuthRequired("ROLE_ADMIN")
  @GetMapping("/{id}", produces = ["text/html"])
  fun generatePDFById(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @PathVariable("id") id: Long,
    @RequestHeader("Authorization") token: String? = null,
  ): String {
    val webApplication = JakartaServletWebApplication.buildApplication(request.servletContext)
    val webContext = WebContext(webApplication.buildExchange(request, response), request.locale)
    return templateProcessorFacade.generateDefaultResumePdfById(webContext, id)
  }

}