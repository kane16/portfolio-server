package pl.delukesoft.portfolioserver.application.template

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.context.WebContext
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import pl.delukesoft.portfolioserver.adapters.template.TemplateProcessorFacade

@RestController
@RequestMapping("/pdf")
class TemplateProcessorController(
    val templateProcessorFacade: TemplateProcessorFacade
) {

    @GetMapping(produces = ["text/html"])
    fun generatePDF(request: HttpServletRequest, response: HttpServletResponse): String {
        val webApplication = JakartaServletWebApplication.buildApplication(request.servletContext)
        val webContext = WebContext(webApplication.buildExchange(request, response), request.locale)
        return templateProcessorFacade.generateDefaultResumePdf(webContext)
    }

}