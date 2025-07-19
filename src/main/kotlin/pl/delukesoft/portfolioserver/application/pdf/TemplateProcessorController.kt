package pl.delukesoft.portfolioserver.application.pdf

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.context.WebContext
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import pl.delukesoft.portfolioserver.adapters.auth.AuthRequired
import pl.delukesoft.portfolioserver.application.filter.PortfolioSearch

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
    @RequestParam("skills", required = false) skills: List<String> = emptyList(),
    @RequestParam("domains", required = false) domains: List<String> = emptyList(),
    @RequestParam("business", required = false) business: List<String> = emptyList(),
    @RequestHeader("Authorization") token: String? = null
  ): String {
    val webApplication = JakartaServletWebApplication.buildApplication(request.servletContext)
    val webContext = WebContext(webApplication.buildExchange(request, response), request.locale)
    return templateProcessorFacade.generateDefaultResumePdf(
      webContext,
      extractPortfolioSearch(skills, domains, business)
    )
  }

  @AuthRequired("ROLE_ADMIN")
  @GetMapping("/{id}", produces = ["text/html"])
  fun generatePDFById(
    request: HttpServletRequest,
    response: HttpServletResponse,
    @PathVariable("id") id: Long,
    @RequestParam("skills", required = false) skills: List<String> = emptyList(),
    @RequestParam("domains", required = false) domains: List<String> = emptyList(),
    @RequestParam("business", required = false) business: List<String> = emptyList(),
    @RequestHeader("Authorization") token: String? = null,
  ): String {
    val webApplication = JakartaServletWebApplication.buildApplication(request.servletContext)
    val webContext = WebContext(webApplication.buildExchange(request, response), request.locale)
    return templateProcessorFacade.generateDefaultResumePdfById(webContext, id, extractPortfolioSearch(skills, domains, business))
  }

  private fun extractPortfolioSearch(
    skills: List<String>,
    domains: List<String>,
    business: List<String>
  ): PortfolioSearch? {
    if (skills.isNotEmpty() || domains.isNotEmpty() || business.isNotEmpty()) {
      return PortfolioSearch(business, skills, domains)
    }
    return null
  }

}