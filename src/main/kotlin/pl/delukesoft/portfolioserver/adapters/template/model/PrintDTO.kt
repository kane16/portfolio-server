package pl.delukesoft.portfolioserver.adapters.template.model

import org.thymeleaf.context.WebContext

interface PrintDTO {

  fun attachDataToContext(context: WebContext)
  fun getResumeId(): Long
  override fun toString(): String

}