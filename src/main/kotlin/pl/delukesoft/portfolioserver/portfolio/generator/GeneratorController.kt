package pl.delukesoft.portfolioserver.portfolio.generator

import com.itextpdf.html2pdf.HtmlConverter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import org.thymeleaf.context.WebContext
import org.thymeleaf.spring6.SpringTemplateEngine
import pl.delukesoft.portfolioserver.portfolio.ResumeRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.FileOutputStream

@RestController
@RequestMapping("/pdf")
class GeneratorController(
    val templateEngine: SpringTemplateEngine,
    val resumeRepository: ResumeRepository
) {

    @GetMapping(produces = ["text/html"])
    fun generatePDF(request: HttpServletRequest, response: HttpServletResponse): String {
        val resume = resumeRepository.findFirstByOrderByLastModifiedDesc()
        val webApplication = JakartaServletWebApplication.buildApplication(request.servletContext)
        val webContext = WebContext(webApplication.buildExchange(request, response), request.locale)
        webContext.setVariable("skills", resume?.skills?.map { it.name }?.toList())
        return templateEngine.process("portfolio-template", webContext)
    }
}