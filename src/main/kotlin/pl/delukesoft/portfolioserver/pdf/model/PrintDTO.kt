package pl.delukesoft.portfolioserver.pdf.model

import org.thymeleaf.context.WebContext

interface PrintDTO {

  fun attachDataToContext(context: WebContext)

}