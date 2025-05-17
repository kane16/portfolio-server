package pl.delukesoft.portfolioserver.portfolio.generator.model

import org.thymeleaf.context.Context
import org.thymeleaf.context.WebContext

interface PrintDTO {

  fun attachDataToContext(context: WebContext)

}