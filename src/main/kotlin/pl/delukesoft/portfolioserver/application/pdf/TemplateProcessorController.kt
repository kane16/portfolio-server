package pl.delukesoft.portfolioserver.application.pdf

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import org.thymeleaf.context.WebContext
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired

@RestController
@RequestMapping("/pdf")
@Tag(name = "PDF", description = "PDF / HTML resume generation")
class TemplateProcessorController(
  val templateProcessorFacade: TemplateProcessorFacade
) {

  @AuthRequired(anonymousAllowed = true)
  @GetMapping(produces = ["text/html"])
  @Operation(
    summary = "Generate HTML representation of default resume for PDF conversion",
    description = "Generate an HTML representation of the default resume for PDF conversion"
  )
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
  @Operation(
    summary = "Generate resume PDF by ID",
    description = "Generate an HTML representation of a specific resume by ID for PDF conversion. Requires ADMIN role."
  )
  @SecurityRequirement(name = "Bearer Authentication")
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