package pl.delukesoft.portfolioserver.portfolio.generator

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.context.WebContext
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import pl.delukesoft.portfolioserver.portfolio.ResumeRepository

@RestController
@RequestMapping("/pdf")
class GeneratorController(
    val generationFacade: GenerationFacade
) {

    @GetMapping(produces = ["text/html"])
    fun generatePDF(request: HttpServletRequest, response: HttpServletResponse): String {
        val webApplication = JakartaServletWebApplication.buildApplication(request.servletContext)
        val webContext = WebContext(webApplication.buildExchange(request, response), request.locale)
        return generationFacade.generateDefaultResumePdf(webContext)
    }
}