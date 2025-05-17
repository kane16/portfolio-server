package pl.delukesoft.portfolioserver.portfolio.generator.model

import org.thymeleaf.context.Context
import org.thymeleaf.context.WebContext

data class ResumePrintDTO(
  val fullname: String,
  val imageSource: String,
  val title: String,
  val summary: String,
  val skills: List<String>,
  val languages: List<String>,
  val projects: List<ProjectDTO>,
  val workHistory: List<ProjectDTO>,
  val hobbies: List<String>
): PrintDTO {

  override fun attachDataToContext(context: WebContext) {
    context.setVariable("fullname", fullname)
    context.setVariable("imageSource", imageSource)
    context.setVariable("title", title)
    context.setVariable("summary", summary)
    context.setVariable("skills", skills)
    context.setVariable("languages", languages)
    context.setVariable("projects", projects)
    context.setVariable("workHistory", workHistory)
    context.setVariable("hobbies", hobbies)
  }

}
