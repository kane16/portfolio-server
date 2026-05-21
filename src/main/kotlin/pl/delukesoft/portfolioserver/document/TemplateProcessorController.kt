package pl.delukesoft.portfolioserver.document

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import org.thymeleaf.context.WebContext
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import pl.delukesoft.authplugin.security.AuthContext
import pl.delukesoft.authplugin.security.AuthRequired
import pl.delukesoft.authplugin.security.JwtService
import pl.delukesoft.portfolioserver.resume.author.PortfolioAuthor

@RestController
@RequestMapping("/pdf")
@Tag(name = "PDF", description = "PDF / HTML resume generation")
class TemplateProcessorController(
  val templateProcessorFacade: TemplateProcessorFacade,
  private val jwtService: JwtService,
  private val authContext: AuthContext<PortfolioAuthor>
) {

  @AuthRequired(allowAnonymous = true, app = "portfolio")
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
    authenticateOptionalUser(token)
    val webApplication = JakartaServletWebApplication.buildApplication(request.servletContext)
    val webContext = WebContext(webApplication.buildExchange(request, response), request.locale)
    return templateProcessorFacade.generateDefaultResumePdf(
      webContext
    )
  }

  @AuthRequired(role = "ROLE_ADMIN", app = "portfolio")
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

  private fun authenticateOptionalUser(token: String?) {
    authContext.setAuthor(null)
    if (token.isNullOrBlank()) {
      authContext.setUser(null)
      authContext.setToken(null)
      return
    }
    authContext.setToken(token)
    authContext.setUser(jwtService.getUser(token))
  }

}
