package pl.delukesoft.portfolioserver.application.template.model

import org.thymeleaf.context.WebContext

interface PrintDTO {

  fun attachDataToContext(context: WebContext)
  fun getResumeId(): Long

}