package pl.delukesoft.portfolioserver.application.portfolio.model

import org.thymeleaf.context.WebContext
import pl.delukesoft.portfolioserver.application.pdf.model.PrintDTO

data class PortfolioDTO(
  val id: Long,
  val fullname: String,
  val imageSource: String,
  val title: String,
  val summary: String,
  val skills: List<SkillDTO>,
  val languages: List<LanguageDTO>,
  val sideProjects: List<ProjectDTO>,
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
    context.setVariable("sideProjects", sideProjects)
    val (workHistory1, workHistory2) = partitionToHalf(workHistory)
    context.setVariable("workHistory1", workHistory1)
    context.setVariable("workHistory2", workHistory2)
    context.setVariable("hobbies", hobbies)
  }

  override fun getResumeId(): Long {
    return id
  }

  private fun <T> partitionToHalf(list: List<T>): Pair<List<T>, List<T>> {
    val half = list.size / 2
    return list.take(half) to list.takeLast(list.size - half)
  }

  override fun toString(): String {
    return "ResumePrintDTO(id=$id, fullname='$fullname', imageSource='$imageSource', title='$title', summary='$summary', skills=$skills, languages=$languages, sideProjects=$sideProjects, workHistory=$workHistory, hobbies=$hobbies)"
  }


}
