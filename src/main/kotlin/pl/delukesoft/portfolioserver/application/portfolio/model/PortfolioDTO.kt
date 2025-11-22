package pl.delukesoft.portfolioserver.application.portfolio.model

import org.thymeleaf.context.WebContext
import pl.delukesoft.portfolioserver.application.pdf.model.PrintDTO
import pl.delukesoft.portfolioserver.application.resume.education.EducationDTO
import pl.delukesoft.portfolioserver.domain.resume.shortcut.contact.ContactInfo

data class PortfolioDTO(
  val id: Long,
  val fullname: String,
  val imageSource: String,
  val title: String,
  val summary: String,
  val email: String,
  val skills: List<SkillPortfolioDTO>,
  val languages: List<LanguageDTO>,
  val sideProjects: List<ProjectDTO>,
  val workHistory: List<ProjectDTO>,
  val hobbies: List<String>,
  val education: List<EducationDTO>,
  val contact: ContactInfo? = null
): PrintDTO {

  override fun attachDataToContext(context: WebContext) {
    context.setVariable("fullname", fullname)
    context.setVariable("imageSource", imageSource)
    context.setVariable("title", title)
    context.setVariable("summary", summary)
    context.setVariable("email", email)
    context.setVariable("skills", skills)
    context.setVariable("languages", languages)
    context.setVariable("sideProjects", sideProjects)
    context.setVariable("workHistory", workHistory)
    context.setVariable("hobbies", hobbies)
    context.setVariable("education", education)
    context.setVariable("contact", contact)
  }

  override fun getResumeId(): Long {
    return id
  }

  override fun toString(): String {
    return "ResumePrintDTO(id=$id, fullname='$fullname', imageSource='$imageSource', title='$title', summary='$summary', skills=$skills, languages=$languages, sideProjects=$sideProjects, workHistory=$workHistory, hobbies=$hobbies, education=$education)"
  }


}
