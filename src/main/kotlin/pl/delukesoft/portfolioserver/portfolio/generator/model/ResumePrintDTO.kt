package pl.delukesoft.portfolioserver.portfolio.generator.model

import org.thymeleaf.context.Context
import org.thymeleaf.context.WebContext

data class ResumePrintDTO(
  val fullname: String,
  val imageSource: String,
  val title: String,
  val summary: String,
  val skills: List<SkillDTO>,
  val languages: List<LanguageDTO>,
  val projects: List<ProjectDTO>,
  val workHistory: List<ProjectDTO>,
  val hobbies: List<String>
): PrintDTO {

  override fun attachDataToContext(context: WebContext) {
    context.setVariable("fullname", fullname)
    context.setVariable("imageSource", imageSource)
    context.setVariable("title", title)
    context.setVariable("summary", summary)
    val (skills1, skills2) = partitionToHalf(skills)
    context.setVariable("skills1", skills1)
    context.setVariable("skills2", skills2)
    context.setVariable("languages", languages)
    context.setVariable("projects", projects)
    context.setVariable("workHistory", workHistory)
    context.setVariable("hobbies", hobbies)
  }

  private fun partitionToHalf(list: List<SkillDTO>): Pair<List<SkillDTO>, List<SkillDTO>> {
    val half = list.size / 2
    return list.take(half) to list.takeLast(list.size - half)
  }

}
